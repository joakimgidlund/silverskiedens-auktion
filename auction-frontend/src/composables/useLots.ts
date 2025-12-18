import { ref } from "vue";
import type { Lot } from "../types/Lot";

export function useLots() {
  const lots = ref<Lot[]>([]);
  const loading = ref(false);
  const error = ref<string | null>(null);
  
  const loadLots = async () => {
    loading.value = true;
    error.value = null;

    try {
      const res = await fetch("/lots/all", { credentials: "include" });
      if (!res.ok) throw new Error("Failed to fetch lots");
      const data = await res.json();
      lots.value = data;
    } catch (e) {
      error.value = (e as Error).message;
    } finally {
      loading.value = false;
    }
  };

  const createLot = async (
    title: string,
    description: string,
    imagePath: string,
    estimate: number
  ) => {
    loading.value = true;
    error.value = null;

    try {
      const res = await fetch("/lots/create-lot", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          title,
          description,
          imagePath,
          published: false,
          estimate,
        }),
      });
      if (!res.ok) throw new Error("Failed to create lot");
      const createdLot: Lot = await res.json();
      lots.value.push(createdLot);
      return createdLot;
    } catch (e) {
      error.value = (e as Error).message;
      throw e;
    } finally {
      loading.value = false;
    }
  };

  const publishLot = async (lot: Lot) => {
    await fetch(`/lots/${lot.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: lot.title,
        description: lot.description,
        imagePath: lot.imagePath,
        published: !lot.published,
      }),
    });
    lot.published = !lot.published;
  };

  const deleteLot = async (lot: Lot) => {
    try {
      const res = await fetch(`/lots/${lot.id}`, {
        method: "DELETE",
      });
      if (!res.ok) {
        throw new Error("Can't delete " + lot.title);
      }
      lots.value = lots.value.filter((l) => l.id !== lot.id);
      console.log(lots.value);
    } catch (e) {
      console.log(e);
    }
  };
  return {
    lots,
    loading,
    error,
    loadLots,
    createLot,
    publishLot,
    deleteLot,
  };
}
