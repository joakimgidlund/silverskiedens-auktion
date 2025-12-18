<script setup lang="ts">
import { useToast } from "primevue";
import { useLots } from "../composables/useLots";
import type { FormSubmitEvent } from "@primevue/forms";

const { createLot } = useLots();
const toast = useToast();

const onFormSubmit = async ($form: FormSubmitEvent) => {
    await createLot($form.values.title, $form.values.description, $form.values.image);
    toast.add({
        summary: "Föremål skapat.",
        life: 5 * 1000,
        severity: "success"
    });
    $form.reset();
};
</script>

<template>
    <div class="w-100 mx-auto">
        <h2 :style="{ fontFamily: 'Playfair Display, serif' }">Registrera föremål</h2>
        <Form @submit="onFormSubmit" class="flex justify-center flex-col gap-4">
            <div class="flex flex-col gap-1">
                <InputText name="title" placeholder="Titel" />
                <Message size="small" severity="secondary" variant="simple">Föremålets titel</Message>
            </div>
            <div class="flex flex-col gap-1">
                <InputText name="description" placeholder="Beskrivning" :feedback="false" toggleMask fluid />
                <Message size="small" severity="secondary" variant="simple">Beskriv föremålet</Message>
            </div>
            <div class="flex flex-col gap-1">
                <InputText name="image" placeholder="Bild-URL" />
                <Message size="small" severity="secondary" variant="simple">Om föremålet ska ha en bild, ange en länk
                </Message>
            </div>
            <Button class="w-1/4 ml-auto" type="submit" severity="primary" label="Skapa"></Button>
        </Form>
    </div>
</template>