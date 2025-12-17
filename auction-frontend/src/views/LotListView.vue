<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useLots } from "../composables/useLots";
import DataView from "primevue/dataview";
import Button from "primevue/button"
import Tag from "primevue/tag"

const lots = ref<{ id: number; title: string; description: string }[]>([])
const loading = ref(false)
const error = ref<string | null>(null)

const { loadLots, publishLot } = useLots()

onMounted(async () => {
  loading.value = true
  error.value = null
  try {
    lots.value = await loadLots()
  } catch (e) {
    error.value = "Kunde inte hämta lots"
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="mt-1">
    <h1>Alla Lots</h1>
    <DataView :value="lots">
        <template #list="{ items }">
            <div v-for="lot in items" :key="lot.id" class="p-3 border-bottom flex justify-between align-items-center">
                <div>
                    <h3>{{ lot.title }}</h3>
                    <p>{{ lot.description }}</p>
                    <Tag :severity="lot.published ? 'success' : 'warning' "
                        :value="lot.published ? 'Published' : 'Draft'"/>
                </div>
            <Button :label="lot.published ? 'Unpublish' : 'Publish'" @click="publishLot(lot)"/>
            </div>
        </template>
    </DataView>
  </div>
</template>
