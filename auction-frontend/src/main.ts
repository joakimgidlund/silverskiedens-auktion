import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import { PrimeVue } from "@primevue/core";
import Aura from "@primeuix/themes/aura";
import { router } from "./router.ts";
// import { initAuth } from "./composables/useAuth.ts";
import { ToastService } from "primevue";

// initAuth();

const app = createApp(App);
app.use(
  PrimeVue,
  {
    theme: {
      preset: Aura,
    },
  },
);
app.use(router);
app.use(ToastService);
app.mount("#app");
