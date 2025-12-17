import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import { PrimeVue } from "@primevue/core";
import Aura from "@primeuix/themes/aura";
import { router } from "./router.ts";
// import { initAuth } from "./composables/useAuth.ts";
import { ToastService } from "primevue";
import Button from "primevue/button";
import Toolbar from "primevue/toolbar";


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
app.component("Button", Button);
app.component("Toolbar", Toolbar)
app.mount("#app");
