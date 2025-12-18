<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useLots } from "../composables/useLots";
import { useConfirm } from "primevue/useconfirm"
import DataView from "primevue/dataview";
import Button from "primevue/button"
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
    message: "Är du säker att du vill ta bort det valda föremålet?",
    rejectProps: {
      label: "Avbryt",
      severity: "secondary",
      outlined: true
    },
    acceptProps: {
      label: "Ta bort",
      severity: "danger"
    },
    accept: async () => {
      await deleteLot(lot)
      toast.add({ severity: "info", summary: "Bekräftat", detail: "Föremål borttaget.", life: 3000 })
    }
  })
}
</script>

<template>
  <DataView :value="lots">
    <template #list="slotProps">
      <div class="flex flex-col">
        <div v-for="(lot, index) in slotProps.items" :key="index">
          <div class="flex flex-col sm:flex-row sm:items-center p-6 gap-4"
            :class="{ 'border-t border-surface-200 dark:border-surface-700': index !== 0 }">
            <div class="flex flex-col md:flex-row justify-between md:items-center flex-1 gap-6">
              <div class="flex flex-row md:flex-col justify-between items-start gap-2">
                <div>
                  <span class="font-medium text-surface-500 dark:text-surface-400 text-sm">{{ lot.description
                  }}</span>
                  <div class="text-lg font-medium mt-2">{{ lot.title }}</div>
                </div>
              </div>
              <div class="flex flex-col md:items-end gap-8">
                <div class="flex flex-col gap-2">
                  <Button :label="lot.published ? 'Dra ut' : 'Publicera'" :loading="loading"
                    :disabled="loading" @click="publishLot(lot)" />
                  <Button label="Ta bort" severity="danger" :loading="loading" :disabled="loading"
                    @click="handleDelete($event, lot)" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </DataView>
  <ConfirmPopup></ConfirmPopup>
</template>
