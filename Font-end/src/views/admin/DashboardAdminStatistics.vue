<template>
  <v-app>
    <AdminToolbar />
    <AdminDrawer />
    <v-card flat>
      <v-card-title primary-title class="ml-5">
        <v-layout row wrap align-center>
          <v-flex xs4></v-flex>
          <v-flex xs2>
            <v-layout row wrap align-center>
              <v-flex xs3>De</v-flex>
              <v-flex xs7>
                <v-text-field label="dd/mm/aaaa" v-model="inicio"></v-text-field>
              </v-flex>
            </v-layout>
          </v-flex>

          <v-flex xs2>
            <v-layout row wrap align-center>
              <v-flex xs3>até</v-flex>
              <v-flex xs7>
                <v-text-field label="dd/mm/aaaa" v-model="fim"></v-text-field>
              </v-flex>
            </v-layout>
          </v-flex>

          <v-flex xs2>
            <v-layout row wrap align-center>
              <v-flex xs7>
                <v-btn color="success" @click="periodo()">text</v-btn>
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-card-title>
      <v-card-text>
        <VueApexCharts width="500" type="line" :options="options" :series="series"></VueApexCharts>
      </v-card-text>
    </v-card>
    <AdminFooter />
  </v-app>
</template>

<style>
html {
  overflow-y: hidden;
  overflow-x: hidden;
}
</style>

<script>
import AdminDrawer from "../../components/admin/AdminDrawer.vue";
import AdminFooter from "../../components/admin/AdminFooter.vue";
import AdminToolbar from "../../components/admin/AdminToolbar.vue";
import VueApexCharts from "vue-apexcharts";
import axios from "axios";

export default {
  data: () => ({
    options: {
      chart: {
        id: "vuechart-example"
      },
      xaxis: {
        type: "datetime"
      }
    },
    series: [
      {
        name: "Valor de custo",
        data: [
          {
            x: "08/09/2019",
            y: 96
          },
          {
            x: "08/11/2019",
            y: 10
          },
          {
            x: "08/15/2019",
            y: 10
          },
          {
            x: "08/21/2019",
            y: 100
          }
        ]
      },
      {
        name: "Valor de venda",
        data: []
      }
    ],

    inicio: "",
    fim: "",
    vendas: [],
    dataCadastro: []
  }),

  methods: {
    async getVendas() {
      let vendas = await axios.get("/ConsultarVenda?OPERACAO=CONSULTAR");
      vendas.data.entidades.forEach(venda => {
        let valorCusto = 0;
        let valorVenda = 0;
        venda.itens.forEach(item => {
          valorCusto += item.valorCusto;
          valorVenda += item.valorTotal;
        });

        this.vendas.push({
          dataCadastro: this.formatteDate(venda.dataCadastro),
          valorVenda: Number(valorVenda),
          valorCusto: Number(valorCusto)
        });
      });

      var result = new Object();
      this.vendas.filter(function(i) {
        if (result.hasOwnProperty(i.dataCadastro)) {
          result[i.dataCadastro].valorVenda += i.valorVenda;
          result[i.dataCadastro].valorCusto += i.valorCusto;
        } else {
          result[i.dataCadastro] = new Object();
          result[i.dataCadastro].valorVenda = i.valorVenda;
          result[i.dataCadastro].valorCusto = i.valorCusto;
        }
      });
    },

    periodo() {
      let newData = [];

      this.series[0].data.forEach(e => {
        if (
          this.dataFormatoAmericano(e.x) >=
            this.dataFormatoAmericano(this.inicio) &&
          this.dataFormatoAmericano(e.x) <= this.dataFormatoAmericano(this.fim)
        ) {
          newData.push(e);
        }
      });
      this.series[0] = [
        {
          data: newData
        }
      ];
    },

    dataFormatoAmericano(data) {
      let parts = data.split("/");
      return new Date(parts[2], parts[1] - 1, parts[0]);
    },

    formatteDate(data) {
      let vetData = data.split(" ");
      let newFormatte = `${vetData[1]} ${vetData[2]} ${vetData[5]}`;
      let d = new Date(newFormatte);
      return [d.getDate(), d.getMonth() + 1, d.getFullYear()]
        .map(n => (n < 10 ? `0${n}` : `${n}`))
        .join("/");
    }
  },

  created() {
    this.getVendas();
  },

  components: {
    AdminDrawer,
    AdminFooter,
    AdminToolbar,
    VueApexCharts
  }
};
</script>
