import { createRouter, createWebHistory } from "vue-router";

import RegisterView from "./views/RegisterView.vue";
import LoginView from "./views/LoginView.vue";
import StartView from "./views/StartView.vue";
import { isLoggedIn } from "./composables/useAuth.ts";

const routes = [
  { path: "/", component: StartView },
  {
    path: "/login",
    component: LoginView,
    beforeEnter: (to, from, next) => {
      if (isLoggedIn.value) next("/");
      else next();
    },
  },
  {
    path: "/register",
    component: RegisterView,
    beforeEnter: (to, from, next) => {
      if (isLoggedIn.value) next("/");
      else next();
    },
  },
];

export const router = createRouter({
  history: createWebHistory(),
  routes,
});
