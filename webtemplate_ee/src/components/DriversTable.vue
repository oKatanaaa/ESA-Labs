<template>
  <div>
    <v-row>
      <v-col>
        <h1> Drivers table</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-btn @click="addNewRowStart">Add new driver</v-btn>
      </v-col>
    </v-row>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th
              class="text-center"
              v-for="item in table_header"
              v-bind:key="item"
            >
              {{ item }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in table_data" v-bind:key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.email }}</td>
            <td>
              <v-btn icon @click="editRowStart(item)">
                <v-icon>mdi-pen</v-icon>
              </v-btn>
              <v-btn icon @click="deleteRow(item.id)">
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    
    <dialog-example
      :dialog="showDialog"
      :item="newItem"
      :title="title"
      @close="closeDialog"
      @next="nextFunction"
    />
  </div>
</template>

<script>
// Change this import in respect to the technology currently using in the backend 
import { eeGetAll, eeDeleteItem, eeAddNewItem, eeUpdateItem } from "../endpoints/table_drivers_endpoints";
import DialogExample from "./DialogExample.vue";

export default {
  components: { DialogExample },
  name: "DriversTable",
  data() {
    return {
      table_header: ["Id", "Name", "Email"],
      table_data: [],
      showDialog: false,
      newItem: {name: '', email: ''},
      editingItemIndex: -1,
      title: '',
    };
  },
  computed: {},
  mounted() {
    this.getAllRows();
  },
  methods: {
    nextFunction() {
      // Placeholder
    },
    getAllRows() {
      eeGetAll().then((response) => {
          this.table_data = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteRow(itemId) {
      eeDeleteItem(itemId)
        .then(() => {
          this.getAllRows();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    editRowStart(item) {
      this.title = `Modify existing driver #${item.id}`
      this.editingItemIndex = item.id
      // Defines fields we need to fill in the dialog
      this.newItem = {'name': item.name, 'email': item.email}
      this.nextFunction = this.editRowEnd
      this.showDialog = true;
    },
    editRowEnd(item) {
      // In case of javaee 
      // addNewItem(item)
      // In case of Srping 
      const body = {'name': item.name, 'email': item.email}
      // params = {'childId': 1}
      const params = {}
      eeUpdateItem(this.editingItemIndex, params, body)
        .then((response) => {
          console.log(response.data);
          this.getAllRows();
        })
        .catch((error) => {
          console.log(error);
        });
      this.editingItemIndex = -1
      this.closeDialog()
    },
    addNewRowStart() {
      this.title = 'Add new driver'
      // Defines fields we need to fill in the dialog
      this.newItem = {name: '', email: ''}
      this.nextFunction = this.addNewRowEnd
      this.showDialog = true;
    },
    addNewRowEnd(item) {
      // In case of javaee 
      // addNewItem(item)
      // In case of Srping 
      const body = {'name': item.name, 'email': item.email}
      // params = {'childId': 1}
      const params = {}
      eeAddNewItem(params, body)
        .then((response) => {
          console.log(response.data);
          this.getAllRows();
        })
        .catch((error) => {
          console.log(error);
        });

      this.closeDialog()
    },
    closeDialog() {
      this.showDialog = false
    },
  },
};
</script>

<style scoped>
</style>
