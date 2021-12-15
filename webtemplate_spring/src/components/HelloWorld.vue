<template>
  <div class="hello">
    <h1>ESA Course Web Application Template</h1>
    <drivers-table/>
    <shops-table/>
    <cars-table/>
  </div>
</template>

<script>
import DriversTable from './DriversTable.vue'
import ShopsTable from "./ShopsTable";
import CarsTable from "./CarsTable";
export default {
  components: {CarsTable, ShopsTable, DriversTable },
  name: 'HelloWorld',
  data() {
    return {

    }
  }
}
</script>

<style scoped>

</style>
