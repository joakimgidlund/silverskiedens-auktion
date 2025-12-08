<script setup lang="ts">
import { ref } from 'vue';

  const loggedIn = ref(false);
</script>

<template>
  <div>
    <h1>Homepage</h1>
    <div class="flex flex-row gap-5">
      <RouterLink to="/">Home</RouterLink>
      <RouterLink v-if="!loggedIn" to="/login">
        <p class="underline">Login</p>
      </RouterLink>
      <RouterLink v-if="!loggedIn" to="/register">Register</RouterLink>
      <RouterLink to="/register">Products</RouterLink>
    </div>
  </div>
  <div>
    <RouterView />
  </div>
</template>
