<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useAuctions } from "../composables/useAuctions";
import type { AuctionLot } from "../types/AuctionLot";
import { isLoggedIn } from "../composables/useAuth";
import Button from "primevue/button";
import DataView from "primevue/dataview";

const { auctions, loadAuctions, placeBid } = useAuctions();
const loading = ref(false);
const error = ref<string | null>(null);
const bidAmounts = ref<Record<number, number>>({})

onMounted(async () => {
  await loadAuctions();
  auctions.value.forEach(a => bidAmounts.value[a.auctionId] = 0);
});

function submitBid(auction: AuctionLot) {
  if (!isLoggedIn.value) {
    alert("Du måste vara inloggad för att lägga ett bud!");
    return;
  }

  const bidAmount = Number(bidAmounts.value[auction.auctionId])
  console.log("why is this not loggin?" + bidAmount);
  console.log(bidAmounts.value[auction.auctionId]);

  if (!bidAmount || bidAmount <= auction.currentBid) {
    alert("Skriv in en giltig summa");
    return;
  }

  bidHandler(auction.auctionId, bidAmount)
  loading.value = true;
  bidAmounts.value[auction.auctionId] = 0
};

async function bidHandler(id: number, amount: number) {
  try {
    await placeBid(id, amount);
    alert("Bud lagt!");
    await loadAuctions();
  } catch (e) {
    error.value = (e as Error).message;
    console.log(error.value, e);
  } finally {
    loading.value = false;
  }
}

</script>

<template>
  <div class="p-4">
    <h2 :style="{ fontFamily: 'Playfair Display, serif' }">Auktioner</h2>

    <DataView :value="auctions" layout="grid" class="p-mt-4">
      <template #grid="slotProps">
        <div class="p-grid p-nogutter">
          <div v-for="(item, index) in slotProps.items" :key="index" class="p-col-12 p-md-6 p-lg-4 p-p-2">
            <div class="p-card">
              <img :src="item.imagePath" :alt="item.title" class="p-mb-2"
                style="width:100%; height:200px; object-fit:cover;" />
              <div class="p-card-content">
                <h3>{{ item.title }}</h3>
                <p>{{ item.description }}</p>
                <p><strong>Värdering:</strong> {{ item.estimate }}</p>
                <p><strong>Aktuellt bud:</strong> {{ item.currentBid }}</p>
                <p><strong>Avslutas:</strong> {{ (item.endTime) }}</p>

                <input type="number" v-model="bidAmounts[item.auctionId]" :min="item.currentBid + 1"
                  placeholder="Ditt bud" class="p-inputtext p-mt-2 p-mb-2" />
                <Button label="Lägg bud" class="p-mt-1" @click="submitBid(item)" :loading="loading" />
              </div>
            </div>
          </div>
        </div>
      </template>
    </DataView>

    <div v-if="error" class="p-mt-4 p-text-red-600">{{ error }}</div>
  </div>
</template>
