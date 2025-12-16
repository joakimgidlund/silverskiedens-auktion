<script setup>
import { useToast } from 'primevue';
import { loginUser } from '../composables/useAuth';
import { ref } from "vue";
import { useRouter } from "vue-router"

const formRef = ref(null);
const badAuth = ref(false);
const router = useRouter();

const toast = useToast();

function onFormSubmit($form) {
    console.log($form.values)
    login({
        username: $form.values.username,
        password: $form.values.password
    })
}


async function login(userInfo) {
    try {
        const resp = await fetch("http://localhost:8081/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify({
                username: userInfo.username,
                password: userInfo.password
            })
        })

        if (!resp.ok) {
            throw new Error(`Response: ${resp.status}`)
        }
        const jwt = await resp.text();
        loginUser(jwt);
        badAuth.value = false;
        toast.add({
            summary: "Login successful.",
            life: 5 * 1000,
            severity: "success"
        });
        router.push("/")
    } catch (error) {
        console.log(error);
        formRef.value?.reset();
        badAuth.value = true;
    }
}
</script>

<template>
    <div class="w-1/6 mx-auto">
        <Form v-slot="$form" ref="formRef" @submit="onFormSubmit" :validateOnUpdate="true"
            class="flex justify-center flex-col gap-4">
            <div class="flex flex-col gap-1">
                <InputText name="username" type="text" placeholder="Username" fluid />
            </div>
            <div class="flex flex-col gap-1">
                <Password type="text" name="password" placeholder="Password" :feedback="false" toggleMask fluid />
            </div>
            <Message v-if="badAuth" severity="error">Could not find those credentials.</Message>
            <Button class="w-1/4 ml-auto" type="submit" severity="primary" label="Login" />
        </Form>
    </div>
</template>