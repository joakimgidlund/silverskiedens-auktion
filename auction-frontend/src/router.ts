import { createRouter, createWebHistory } from "vue-router";

import RegisterView from "./views/RegisterView.vue"
import LoginView from "./views/LoginView.vue";
import StartView from "./views/StartView.vue";
import { isLoggedIn } from "./composables/useAuth.ts";
import CreateLotView from "./views/CreateLotView.vue";
import LotListView from "./views/LotListView.vue";
import AuctionView from "./views/AuctionView.vue";

const routes = [
  { path: "/", component: StartView },
  {
    path: "/login",
    component: LoginView,
    beforeEnter: () => {
      if (isLoggedIn.value) return "/";
    },
  },
  {
    path: "/register",
    component: RegisterView,
    beforeEnter: () => {
      if (isLoggedIn.value) return "/";
    },
  },
  {
    path: "/create-lot",
    component: CreateLotView,
    beforeEnter: () => {
      if (!isLoggedIn.value) return "/";
    },
  },
  {
    path: "/lots",
    component: LotListView,
    beforeEnter: () => {
      if (!isLoggedIn.value) return "/";
    }
  },
  {
    path: "/auctions",
    component: AuctionView,
    beforeEnter:() => {
      if (!isLoggedIn.value) return "/";
    }
  },
];

export const router = createRouter({
  history: createWebHistory(),
  routes,
});
