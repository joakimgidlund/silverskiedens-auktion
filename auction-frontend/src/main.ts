import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import { PrimeVue } from "@primevue/core";
import Lara from "@primeuix/themes/material";
import { router } from "./router.ts";
import { ToastService } from "primevue";
import ConfirmationService from "primevue/confirmationservice";
import Button from "primevue/button";
import Toolbar from "primevue/toolbar";

const app = createApp(App);
app.use(PrimeVue, {
  theme: {
    preset: Lara,
  },
});
app.use(router);
app.use(ToastService);
app.use(ConfirmationService);
app.component("Button", Button);
app.component("Toolbar", Toolbar);
app.mount("#app");
