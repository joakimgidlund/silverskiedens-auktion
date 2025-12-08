import { createRouter, createWebHistory } from 'vue-router'

import RegisterView from './views/RegisterView.vue'
import LoginView from './views/LoginView.vue'
import StartView from './views/StartView.vue'

const routes = [
    { path: '/', component: StartView },
    { path: '/login', component: LoginView },
    { path: '/register', component: RegisterView },
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
})