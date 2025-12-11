<script setup>
function onFormSubmit(form) {
    console.log(form.values)
    login({
        username: form.values.username,
        password: form.values.password
    })
}

async function login(userInfo) {
    try {
        const resp = await fetch("http://localhost:8081/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                username: userInfo.username,
                password: userInfo.password
            })
        })

        if (!resp.ok) {
            throw new Error(`Response: ${resp.statusText}`)
        }
        const result = await resp.text();
        cookieStore.set("jwt", `Bearer ${result}`)
        console.log(await cookieStore.get("jwt"));
        
    } catch (error) {
        console.log(error);
    }
}
</script>

<template>
    <div class="w-1/6 mx-auto">
        <Form v-slot="$form" :initialValues="initialValues" @submit="onFormSubmit"
            class="flex justify-center flex-col gap-4">
            <div class="flex flex-col gap-1">
                <InputText name="username" type="text" placeholder="Username" />
                <Message v-if="$form.username?.invalid" severity="error" size="small" variant="simple">{{
                    $form.username.error?.message }}</Message>
            </div>
            <div class="flex flex-col gap-1">
                <Password type="text" name="password" placeholder="Password" :feedback="false" toggleMask fluid />
                <Message v-if="$field?.invalid" severity="error" size="small" variant="simple">{{ $field.error?.message
                }}</Message>
            </div>
            <Button class="w-1/4 ml-auto" type="submit" severity="primary" label="Login"></Button>
        </Form>
    </div>
</template>