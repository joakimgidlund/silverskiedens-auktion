<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useAuctions } from "../composables/useAuctions";
import type { AuctionLot } from "../types/AuctionLot";
import { isLoggedIn } from "../composables/useAuth";
import Button from "primevue/button";
import DataView from "primevue/dataview";
import type { Form, FormSubmitEvent } from "@primevue/forms";
import { useToast } from "primevue/usetoast";

const { auctions, loadAuctions, placeBid } = useAuctions();
const loading = ref(false);
const error = ref<string | null>(null);
const toast = useToast()
// const bidAmounts = ref<Record<number, number>>({})

onMounted(async () => {
  await loadAuctions();
});

function submitBid(form: FormSubmitEvent, auction: AuctionLot) {
  if (!isLoggedIn.value) {
    alert("Du måste vara inloggad för att lägga ett bud!");
    return;
  }

  const bidAmount = form.values.bid;

  if (!bidAmount || bidAmount <= auction.currentBid) {
    toast.add({
      summary: "Skriv in en giltig summa.",
      life: 5 * 1000,
      severity: "error"
    });
    return;
  }

  bidHandler(auction.auctionId, bidAmount)
  form.reset();
  loading.value = true;
};

async function bidHandler(id: number, amount: number) {
  try {
    await placeBid(id, amount);
    toast.add({
      summary: "Bud lagt!",
      life: 5 * 1000,
      severity: "success"
    });
    const auction = auctions.value.find(a => a.auctionId === id);
    if (auction) {
      auction.currentBid = amount;
    }
  } catch (e) {
    error.value = (e as Error).message;
    console.log(error.value, e);
  } finally {
    loading.value = false;
  }
}

function onImageError(event: Event) {
  const img = event.target as HTMLImageElement;
  img.src = '/img/silverspoon.avif';
}
</script>

<template>
  <div class="p-4 w-1/2">
    <h2 class="text-2xl" :style="{ fontFamily: 'Playfair Display, serif' }">Auktioner</h2>
    <DataView :value="auctions" v-if="auctions.some(a => a.published)">
      <template #list="slotProps">
        <div class="flex flex-col">
          <div v-for="(item, index) in slotProps.items" :key="item.auctionId">
            <div v-if="item.published" class="flex flex-col sm:flex-row sm:items-center p-6 gap-4"
              :class="{ 'border-t border-surface-200 dark:border-surface-700': index !== 0 }">
              <div class="md:w-40 relative">
                <img class="block xl:block mx-auto rounded w-full" :src="`${item.imagePath}`" :alt="item.name"
                  @error="onImageError" />
              </div>
              <div class="flex flex-col md:flex-row justify-between md:items-center flex-1 gap-6">
                <div class="flex flex-row md:flex-col justify-between items-start gap-2">
                  <div>
                    <span class="font-medium text-surface-500 dark:text-surface-400 text-sm">{{ item.description
                    }}</span>
                    <div class="text-lg font-medium mt-2">{{ item.title }}</div>
                  </div>
                </div>
                <div class="flex flex-col md:items-end gap-8">
                  <div class="flex flex-col md:items-end gap-1">
                    <span class="text-xl font-semibold">Värdering: {{ item.estimate }}kr</span>
                    <span class="text-xl font-semibold">Aktuellt bud: {{ item.currentBid }}kr</span>
                  </div>
                  <Form @submit="submitBid($event, item)" class="flex flex-row gap-2">
                    <div class="flex flex-col gap-2">
                      <InputNumber name="bid" :max="item.currentBid * 5" placeholder="Ditt bud" />
                      <Message size="small" severity="secondary" variant="simple">Lägsta godkända bud är {{
                        item.currentBid * 1.05 }}kr</Message>
                    </div>
                    <Button type="submit" label="Lägg bud" class="p-mt-1" :loading="loading" :disabled="loading" />
                  </Form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </template>
    </DataView>

    <div class="flex flex-row gap-5 mt-2 text-center justify-center" v-if="auctions.filter(a => a.published).length === 0">
      <h1 :style="{ fontFamily: 'Playfair Display, serif' }">Inga auktioner publicerade.</h1>
    </div>
  </div>
</template>
