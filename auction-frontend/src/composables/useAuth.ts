import { ref } from "vue";

export const isLoggedIn = ref(false);
export const token = ref<string | null>(null);

export function loginUser(jwt: string) {
  token.value = jwt;
  isLoggedIn.value = true;
  cookieStore.set("jwt", `Bearer ${jwt}`);
}

export function logoutUser() {
  token.value = null;
  isLoggedIn.value = false;
  cookieStore.delete("jwt");
}

export function initAuth() {
  const savedToken = localStorage.getItem("token");
  if (savedToken) {
    token.value = savedToken;
    isLoggedIn.value = true;
  }
}
