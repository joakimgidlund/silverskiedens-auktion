import { ref } from "vue";
import type { AuctionLot } from "../types/AuctionLot";
import { isLoggedIn, token } from "./useAuth";

export function useAuctions() {
  const auctions = ref<AuctionLot[]>([]);
  const loading = ref(false);
  const error = ref<string | null>(null);

  const loadAuctions = async () => {
    loading.value = true;
    error.value = null;
    try {
      const res = await fetch("/auctions/all", {
        method: "GET",
        credentials: "include",
      });
      const data = await res.json();
      if (!res.ok) throw new Error("Failed to fetch auctions");
      auctions.value = data;

    } catch (e) {
      error.value = (e as Error).message;
    } finally {
      loading.value = false;
    }
  };

  const placeBid = async (auctionId: number, amount: number) => {
    if (!isLoggedIn.value) throw new Error("User not logged in");
    try {
      const res = await fetch(`/bids/${auctionId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token.value}`
        },
        body: JSON.stringify({ bidAmount: amount }),
      });
      if (!res.ok) {
        const data = await res.json();
        throw new Error(data.message || "Failed to place bid");
      }
      return await res.json();
    } catch (e) {
      console.error(e);
      throw e;
    }
  };
  return { auctions, loading, error, loadAuctions, placeBid };
}
