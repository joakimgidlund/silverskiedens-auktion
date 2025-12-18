<script setup lang="ts">
import CreateLotForm from "../components/CreateLotForm.vue";
import { useRouter } from "vue-router";

const router = useRouter();
const handleCreated = () => {
    router.push("/lots");
};
</script>

<template>
    <div class="flex flex-row justify-center mt-20">
        <CreateLotForm @created="handleCreated" />
    </div>
</template>