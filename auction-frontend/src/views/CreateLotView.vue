<script setup lang="ts">
    import CreateLotForm from "../components/CreateLotForm.vue";
    import { useRouter } from "vue-router";

const router = useRouter();
const handleCreated = () => {
    router.push("/lots");
};
</script>

<template>
    <CreateLotForm @created="handleCreated"/>
</template>