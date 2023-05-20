<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated class="q-py-md shadow-4">
      <q-toolbar style="padding-left: 18%;" class="row items-center">
        <div class="row">
          <div class="logo1">
          </div>
          <div class="q-ml-xl logo2">
          </div>
        </div>
        <q-space />
        <div>
          <div class="row nav-routes">
            <div @mouseover="setUnderline(0)" @mouseleave="dropUnderline()" @click="router.push({ name: 'Home' })">Главная
            </div>
            <div @mouseover="setUnderline(1)" @mouseleave="dropUnderline()" @click="router.push({ name: 'Pdf' })">Отчёт
            </div>
            <div @mouseover="setUnderline(2)" @mouseleave="dropUnderline()" @click="router.push({ name: 'Settings' })">
              Настройки</div>
          </div>
          <div class="moving-under-line">
            <div class="under-line"></div>
          </div>
        </div>
        <q-space />
        <q-btn class="q-mr-xl" @click="router.push({ name: 'login' })" flat round color="white" icon="logout" />
      </q-toolbar>
    </q-header>
    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { onMounted } from 'vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter()

const undelineData = [
  { margin: '4px', width: '102px' },
  { margin: '116px', width: '80px' },
  { margin: '204px', width: '128px' },
]
const curUnderlineWidth = ref(undelineData[0])
const curUnderlineMargin = ref(undelineData[0])
function setUnderline(val) {
  curUnderlineWidth.value = undelineData[val].width
  curUnderlineMargin.value = undelineData[val].margin
}
function dropUnderline() {
  if (router.currentRoute.value.name === 'Settings') {
    curUnderlineWidth.value = undelineData[2].width
    curUnderlineMargin.value = undelineData[2].margin
  }
  if (router.currentRoute.value.name === 'Home') {
    curUnderlineWidth.value = undelineData[0].width
    curUnderlineMargin.value = undelineData[0].margin
  }
  if (router.currentRoute.value.name === 'Pdf') {
    curUnderlineWidth.value = undelineData[1].width
    curUnderlineMargin.value = undelineData[1].margin
  }
}
onMounted(() => {
  dropUnderline()
})
</script>
<style lang="scss" scoped>
.logo1 {
  width: 160px;
  height: 32px;
  background-image: url('../assets/rosatom-logo.svg');
  background-repeat: no-repeat;
  background-size: contain;
}

.logo2 {
  width: 120px;
  height: 40px;
  background-image: url('../assets/atom-skills-logo.svg');
  background-repeat: no-repeat;
  background-size: contain;
}

.nav-routes {
  & * {
    font-size: 24px;
    padding: 0 10px 0 10px;
  }

  :hover {
    cursor: pointer;
  }
}

.moving-under-line {
  transition: all 1s ease;

  .under-line {
    transition: all 0.38s ease;
    width: v-bind(curUnderlineWidth);
    background-color: white;
    margin-left: v-bind(curUnderlineMargin);
    height: 2px;
  }
}
</style>
