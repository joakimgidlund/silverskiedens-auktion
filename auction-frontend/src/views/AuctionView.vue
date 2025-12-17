<script setup lang="ts">
    import { ref, onMounted } from "vue";
    import { useAuctions } from "../composables/useAuctions";
    import type { AuctionLot } from "../types/AuctionLot";
    import { isLoggedIn, userId } from "../composables/useAuth";
    import Toast from "primevue/toast";
    import Button from "primevue/button";
    import  DataView  from "primevue/dataview";

    const { auctions, loadAuctions, placeBid } = useAuctions();
    const bidAmount = ref<number | null>(null);
    const selectedAuction = ref<AuctionLot | null>(null);
    const loading = ref(false);
    const error = ref<string | null>(null);
        
    onMounted(loadAuctions);

    const submitBid = async (auction: AuctionLot) => {
        if (!isLoggedIn.value) {
            alert("You must be logged in to place a bid!");
            return;
        }

        if (!bidAmount.value || bidAmount.value <= 0) {
            alert("Please enter a valid bid amount");
            return;
        }

        loading.value = true;
        try {
            await placeBid(auction.auctionId, bidAmount.value);
            alert("Bid submitted!");
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
        <h2>Active Auctions</h2>
        <DataView :value="auctions" layout="grid" class="p-mt-4">
            <template #grid="{ item }">
                <div class="p-card p-mb-3">
                    <img :src="item.imagePath" alt="item.title" class="p-mb-2" style="width:100%; height:200px; object-fit:cover;"/>
                    <div class="p-card-content">
                        <h3>{{ item.title }}</h3>
                        <p>{{ item.description }}</p>
                        <p>Estimate: {{ item.estimate }}</p>
                        <p>Current bid: {{ item.currentBid }}</p>
                        <p>Ends at: {{  new Date(item.endTime).toLocaleString() }}</p>
                        <input type="number" v-model.number="bidAmount" placeholder="Your bid"/>
                        <Button label="Place bid" class="p-mt-2" @click="submitBid(item)" :loading="loading"/>
                    </div>
                </div>
            </template>
        </DataView>
    </div>
</template>