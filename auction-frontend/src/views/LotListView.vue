<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useLots } from "../composables/useLots";
import { useConfirm } from "primevue/useconfirm"
import DataView from "primevue/dataview";
import Button from "primevue/button"
import Tag from "primevue/tag"
import { useToast } from "primevue";
import type { Lot } from "../types/Lot";

// const lots = ref<{ id: number; title: string; description: string }[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const confirm = useConfirm()
const toast = useToast()

const { lots, loadLots, publishLot, deleteLot } = useLots()

onMounted(async () => {
  loading.value = true
  error.value = null
  try {
    await loadLots()
  } catch (e) {
    console.log(e);
  } finally {
    loading.value = false
  }
})

async function handleDelete(event: PointerEvent, lot: Lot) {
  confirm.require({
    target: event.currentTarget as HTMLElement,
    message: "Are you sure want to delete the selected lot?",
    rejectProps: {
      label: "Cancel",
      severity: "secondary",
      outlined: true
    },
    acceptProps: {
      label: "Delete",
      severity: "danger"
    },
    accept: async () => {
      await deleteLot(lot)
      toast.add({ severity: "info", summary: "Confirmed", detail: "Lot deleted.", life: 3000 })
    }
  })
}
</script>

<template>
  <div class="mt-1">
    <h1 :style="{ fontFamily: 'Playfair Display, serif' }">Alla Föremål</h1>
    <DataView :value="lots" dataKey="id" :key="lots.length">
      <template #list="{ items }">
        <div v-for="lot in items" :key="lot.id" class="p-3 border-bottom flex justify-between align-items-center">
          <div>
            <h3>{{ lot.title }}</h3>
            <p>{{ lot.description }}</p>
            <Tag :severity="lot.published ? 'success' : 'warning'" :value="lot.published ? 'Publicerad' : 'Utkast'" />
          </div>
          <Button :label="lot.published ? 'Dra ut' : 'Publicera'" @click="publishLot(lot)" />
          <Button @click="handleDelete($event, lot)" label="Delete" severity="danger" variant="outlined"></Button>
        </div>
      </template>
    </DataView>
    <ConfirmPopup></ConfirmPopup>
  </div>
</template>
