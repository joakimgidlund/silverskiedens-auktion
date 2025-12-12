<script setup lang="ts">
import { ref } from 'vue';
import { isLoggedIn, logoutUser } from './composables/useAuth';
import type { Button } from 'primevue';
import { useRouter } from "vue-router"

const router = useRouter();
</script>

<template>
  <Toolbar>
    <template #start>
      <Button icon="pi pi-plus" class="mr-2" severity="secondary" text />
      <Button icon="pi pi-print" class="mr-2" severity="secondary" text />
      <Button icon="pi pi-upload" severity="secondary" text />
    </template>

    <template #center>
    </template>

    <template #end>
      <RouterLink v-if="!isLoggedIn" to="/login">
        <Button severity="primary" label="Login"></Button>
      </RouterLink>
      <Button v-else label="Logout" @click="logoutUser(); router.push('/login')" />
    </template>
  </Toolbar>
  <div class="flex flex-row gap-5">
    <RouterLink to="/">Home</RouterLink>
    <RouterLink v-if="!isLoggedIn" to="/login">
      <p class="underline">Login</p>
    </RouterLink>
    <RouterLink v-if="!isLoggedIn" to="/register">Register</RouterLink>
    <RouterLink to="/register">Products</RouterLink>
  </div>
  <div>
    <RouterView />
  </div>
  <Toast position="bottom-right"/>
</template>
