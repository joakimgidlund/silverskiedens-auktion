<script setup lang="ts">
import { isLoggedIn, logoutUser } from './composables/useAuth';
import Toolbar from 'primevue/toolbar';
import Button from 'primevue/button';
import { useRouter } from "vue-router"
import { useToast } from 'primevue/usetoast';


const router = useRouter();
const toast = useToast();
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
      <div class="flex flex-row gap-2">
        <RouterLink to="/auctions">
          <Button :style="{ fontFamily: 'Playfair Display, serif' }" label="Auktioner" severity="primary" />
        </RouterLink>
        <RouterLink v-if="isLoggedIn" to="/create-lot">
          <Button :style="{ fontFamily: 'Playfair Display, serif' }" label="Registrera föremål" severity="primary" />
        </RouterLink>
        <RouterLink v-if="isLoggedIn" to="/lots">
          <Button :style="{ fontFamily: 'Playfair Display, serif' }" label="Föremål" severity="primary" />
        </RouterLink>
      </div>
    </template>

    <template #end>
      <div class="flex flex-row gap-2">
        <RouterLink v-if="!isLoggedIn" to="/login">
          <Button :style="{ fontFamily: 'Playfair Display, serif' }" severity="primary" label="Logga in"></Button>
        </RouterLink>
        <Button v-else :style="{ fontFamily: 'Playfair Display, serif' }" label="Logga ut" @click="logoutUser();
        toast.add({
          summary: 'Du är utloggad.', 
          life: 5 * 1000, 
          severity: 'secondary' }); 
          router.push('/login')" />
        <RouterLink v-if="!isLoggedIn" to="/register">
          <Button :style="{ fontFamily: 'Playfair Display, serif' }" severity="primary" label="Registrera"></Button>
        </RouterLink>
      </div>
    </template>

  </Toolbar>
  <div class="flex flex-col items-center justify-center text-center">
    <RouterView />
  </div>
  <Toast position="bottom-right" />
</template>

<!-- <style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}
</style> -->
