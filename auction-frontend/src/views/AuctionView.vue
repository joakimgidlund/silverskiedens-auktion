<script setup lang="ts">
    import { ref, onMounted } from "vue";
    import { useAuctions } from "../composables/useAuctions";
    import type { AuctionLot } from "../types/AuctionLot";
    import { isLoggedIn } from "../composables/useAuth";
    import Button from "primevue/button";
    import  DataView  from "primevue/dataview";

    const { auctions, loadAuctions, placeBid } = useAuctions();
    const bidAmount = ref<number | null>(null);
    const loading = ref(false);
    const error = ref<string | null>(null);
        
    onMounted(loadAuctions);

    const submitBid = async (auction: AuctionLot) => {
        if (!isLoggedIn.value) {
            alert("Du måste vara inloggad för att lägga ett bud!");
            return;
        }

        if (!bidAmount.value || bidAmount.value <= 0) {
            alert("Skriv in en giltig summa");
            return;
        }

        loading.value = true;
        try {
            await placeBid(auction.auctionId, bidAmount.value);
            alert("Bud lagt!");
            await loadAuctions();
            bidAmount.value = null;
        } catch (e) {
            error.value = (e as Error).message;
            alert(error.value);
        } finally {
            loading.value = false;
        }
    };

</script>

<template>
    <div class="p-4">
        <h2 :style="{ fontFamily: 'Playfair Display, serif' }">Auktioner</h2>
        <DataView :value="auctions" layout="grid" class="p-mt-4">
            <template #list="slotProps">
                <div v-for="(item, index) in slotProps.items" :key="index" class="p-card p-mb-3">
                    <img :src="item.imagePath" alt="item.title" class="p-mb-2" style="width:100%; height:200px; object-fit:cover;"/>
                    <div class="p-card-content">
                        <h3>{{ item.title }}</h3>
                        <p>{{ item.description }}</p>
                        <p>Värdering: {{ item.estimate }}</p>
                        <p>Aktuellt bud: {{ item.currentBid }}</p>
                        <p>Avslutas: {{  new Date(item.endTime).toLocaleString() }}</p>
                        <input type="number" v-model.number="bidAmount" placeholder="Ditt bud"/>
                        <Button label="Lägg bud" class="p-mt-2" @click="submitBid(item)" :loading="loading"/>
                    </div>
                </div>
            </template>
        </DataView>
    </div>
</template>