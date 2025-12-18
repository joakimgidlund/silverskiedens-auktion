<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const badRegister = ref(false);
const router = useRouter();

function onFormSubmit($form) {
    console.log($form.values);
    register({
        username: $form.values.username,
        password: $form.values.password,
        email: $form.values.email
    })
}

async function register(registrationData) {
    try {
        const resp = await fetch("/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                username: registrationData.username,
                password: registrationData.password,
                email: registrationData.email
            })
        })

        if (!resp.ok) {
            throw new Error(`Response: ${resp.status}`)
        }
        const result = await resp.text();
        badRegister.value = false;
        router.push("/login");
        console.log(result);

    } catch (error) {
        console.log(error);
        badRegister.value = true;
    }
}

</script>

<template>
    <div class="w-100 mx-auto">
        <Form v-slot="$form" @submit="onFormSubmit"
            class="flex justify-center flex-col gap-4">
            <div class="flex flex-col gap-1">
                <InputText name="username" type="text" placeholder="Användarnamn" />
            </div>
            <div class="flex flex-col gap-1">
                <Password type="text" name="password" placeholder="Lösenord" :feedback="false" toggleMask fluid />
            </div>
            <div class="flex flex-col gap-1">
                <InputText name="email" type="text" placeholder="Email" />
            </div>
            <Message v-if="badRegister" severity="error">Could not find those credentials.</Message>
            <Button class="w-1/4 ml-auto" type="submit" severity="primary" label="Registera"></Button>
        </Form>
    </div>
</template>