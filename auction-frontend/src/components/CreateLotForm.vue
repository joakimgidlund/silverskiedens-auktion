<script setup lang="ts">
    import { ref, onMounted } from "vue";
    import { useLots } from "../composables/useLots";

    const { loadLots, createLot, loading, error } = useLots();

    const title = ref("")
    const description = ref("")
    const imagePath = ref("")

    const submit = async () => {
        await createLot(title.value, description.value, imagePath.value);   
        title.value = ""
        description.value = ""
        imagePath.value = ""
    
}; 
onMounted(loadLots);
</script>

<template>
    <div>
        <h2 :style="{ fontFamily: 'Playfair Display, serif' }">Registrera föremål</h2>
    <form @submit.prevent="submit">
        <input v-model="title" placeholder="Titel"/>
        <input v-model="description" placeholder="Beskrivning" />
        <input v-model="imagePath" placeholder="Bild URL" />

        <Button label="Registrera" :loading="loading" @click="submit"/>
    </form>
        <p v-if="error" class="error">{{ error }}</p>

    
    </div>
</template>