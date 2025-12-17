<script setup lang="ts">
import { isLoggedIn, logoutUser } from './composables/useAuth';
import Toolbar from 'primevue/toolbar';
import Button from 'primevue/button';
import { useRouter } from "vue-router"


const router = useRouter();
</script>

<template>

  <Toolbar>
    <template #start>
      <RouterLink to="/">
        <Button :style="{ fontFamily: 'Playfair Display, serif' }" severity="secondary"
          label="Silverskiedens auktion" />
      </RouterLink>
    </template>

    <template #center>
      <RouterLink to="/auctions">
        <Button :style="{ fontFamily: 'Playfair Display, serif' }" label="Auktioner" class="mr-2" severity="primary" />
      </RouterLink>
    </template>

    <template #end>
      <RouterLink v-if="isLoggedIn" to="/create-lot">
        <Button :style="{ fontFamily: 'Playfair Display, serif' }" label="Registrera föremål" class="mr-2"
          severity="primary" />
      </RouterLink>
      <RouterLink v-if="isLoggedIn" to="/lots">
        <Button :style="{ fontFamily: 'Playfair Display, serif' }" label="Föremål" class="mr-10" severity="primary" />
      </RouterLink>
      <RouterLink v-if="!isLoggedIn" to="/login">
        <Button :style="{ fontFamily: 'Playfair Display, serif' }" severity="primary" label="Login"></Button>
      </RouterLink>
      <Button v-else :style="{ fontFamily: 'Playfair Display, serif' }" label="Logout"
        @click="logoutUser(); router.push('/login')" />
    </template>

  </Toolbar>
  <div id="app" class="app-container">
    <div class="flex flex-row gap-5">
      <RouterLink v-if="!isLoggedIn" to="/login">
        <p class="underline">Login</p>
      </RouterLink>
      <RouterLink v-if="!isLoggedIn" to="/register">Register</RouterLink>

    </div>
    <div>
      <RouterView />
    </div>
  </div>
  <Toast position="bottom-right" />
</template>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}
</style>
