<template>
  <q-layout view="lHh Lpr lFf">
    <q-page-container>
      <q-page class="flex flex-center login-bg column">
        <q-card style="width:300px;" class="bg-primary text-white q-pb-xs">
          <q-card-section>
            <q-input v-model="formData.login" label="логин" filled dark name="firstname" class="q-my-sm" />
            <q-input type="password" v-model="formData.password" label="пароль" filled dark name="password" />
          </q-card-section>
          <q-separator color="white" inset class="q-mx-md"></q-separator>
          <q-card-section>
            <q-btn @click="loginUser(formData.login, formData.password)" dark flat style="width:100%">
              войти
            </q-btn>
          </q-card-section>
        </q-card>
        <div style="height:100px;margin-top: 12px;">
          <transition appear enter-active-class="animated fadeInLeft" leave-active-class="animated fadeOutLeft">
            <q-banner v-if="error" class="bg-red text-white">
              <template v-slot:avatar>
                <q-icon name="report" color="white" />
              </template>
              Пользователь не найден
            </q-banner>
          </transition>
        </div>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useUserStore } from "../stores/UserStore"
import { useRouter } from "vue-router";
import { onMounted } from "vue";

const router = useRouter()
const userStorage = useUserStore()
const formData = reactive({
  login: '',
  pass: ''
})
const error = ref(false)
async function loginUser(login, pass) {
  await userStorage.login(login, pass)
  if (userStorage?.user?.accessToken && userStorage?.user?.refreshToken) {
    router.push('/')
  } else {
    error.value = true
    setTimeout(() => {
      error.value = false
    }, 1500)
  }
}
onMounted(() => {
  userStorage.dropUser()
})
</script>
<style>
.login-bg {
  background-image: url('../assets/login-background.jpg');
  background-size: 100%;
}
</style>
