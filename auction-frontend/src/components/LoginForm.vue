<script setup>

</script>

<template>
    <div class="w-1/6 mx-auto">
        <Form v-slot="$form" :resolver="resolver" :initialValues="initialValues" @submit="onFormSubmit"
            class="flex justify-center flex-col gap-4">
            <div class="flex flex-col gap-1">
                <InputText name="username" type="text" placeholder="Username" />
                <Message v-if="$form.username?.invalid" severity="error" size="small" variant="simple">{{
                    $form.username.error?.message }}</Message>
            </div>
            <div class="flex flex-col gap-1">
                <Password type="text" placeholder="Password" :feedback="false" toggleMask fluid />
                <Message v-if="$field?.invalid" severity="error" size="small" variant="simple">{{ $field.error?.message
                    }}</Message>
            </div>
            <Button class="w-1/4 ml-auto" type="submit" severity="primary" label="Login"></Button>
        </Form>
    </div>
</template>