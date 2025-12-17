import { ref } from "vue";

export const isLoggedIn = ref(false);
export const token = ref<string | null>(null);
export const userId = ref<number | null>(null);

export function loginUser(jwt: string, id: number) {
  token.value = jwt;
  userId.value = id;
  isLoggedIn.value = true;
}

export function logoutUser() {
  token.value = null;
  userId.value = null;
  isLoggedIn.value = false;
  cookieStore.delete("jwt");
}

// export function initAuth() {
//   const savedToken = localStorage.getItem("token");
//   if (savedToken) {
//     token.value = savedToken;
//     isLoggedIn.value = true;
//   }
// }
