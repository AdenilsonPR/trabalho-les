<template>
  <v-app>
    <AdminToolbar />
    <AdminDrawer />
    <v-card flat>
      <v-card-title primary-title class="mt-5">
        <v-layout row wrap align-center>
          <v-flex xs4>
            <span class="display-1 ml-5">Custo x Venda</span>
          </v-flex>
          <v-flex xs2>
            <v-layout row wrap align-center>
              <v-flex xs3>De</v-flex>
              <v-flex xs7>
                <v-text-field label="dd/mm/aaaa" v-model="inicio" data-cy="inicio"></v-text-field>
              </v-flex>
            </v-layout>
          </v-flex>

          <v-flex xs2>
            <v-layout row wrap align-center>
              <v-flex xs3>até</v-flex>
              <v-flex xs7>
                <v-text-field label="dd/mm/aaaa" v-model="fim" data-cy="fim"></v-text-field>
              </v-flex>
            </v-layout>
          </v-flex>

          <v-flex xs2>
            <v-layout row wrap align-center>
              <v-flex xs7>
                <v-btn
                  dark
                  color="deep-orange accent-4"
                  @click="periodo()"
                  data-cy="filtrar"
                >Filtrar</v-btn>
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-card-title>
      <v-card-text>
        <VueApexCharts width="1060" height="490" type="line" :options="options" :series="series"></VueApexCharts>
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
        id: "vuechart-custo-venda"
      },
      xaxis: {
        type: "datetime"
      },
      colors: ["#990000", "#000099"],

      // dataLabels: {
      //   enabled: true
      // },

      grid: {
        borderColor: "#e7e7e7",
        row: {
          colors: ["#f3f3f3", "transparent"], // takes an array which will be repeated on columns
          opacity: 0.5
        }
      },
      markers: {
        size: 5
      },
      legend: {
        position: "top",
        horizontalAlign: "left",
        floating: true,
        offsetY: -5,
        offsetX: 25
      }
    },
    series: [],

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

      let datas = Object.keys(result);
      let valores = Object.values(result);

      let valorCusto = [];
      let valorVenda = [];

      valores.forEach(valor => {
        valorCusto.push(valor.valorCusto);
        valorVenda.push(valor.valorVenda);
      });

      let linhaVenda = [];
      for (let i = 0; i < valores.length; i++) {
        linhaVenda.push({
          x: datas[i],
          y: valorVenda[i]
        });
      }

      let linhaCusto = [];
      for (let i = 0; i < valores.length; i++) {
        linhaCusto.push({
          x: datas[i],
          y: valorCusto[i]
        });
      }

      this.series.push(
        {
          name: "Valor de venda",
          data: linhaVenda
        },
        {
          name: "Valor de custo",
          data: linhaCusto
        }
      );
    },

    periodo() {
      let linhaVenda = [];
      let linhaCusto = [];

      this.series[0].data.forEach(e => {
        if (
          e.x >= this.dataFormatoAmericano(this.inicio) &&
          e.x <= this.dataFormatoAmericano(this.fim)
        ) {
          linhaVenda.push(e);
        }
      });

      this.series[1].data.forEach(e => {
        if (
          e.x >= this.dataFormatoAmericano(this.inicio) &&
          e.x <= this.dataFormatoAmericano(this.fim)
        ) {
          linhaCusto.push(e);
        }
      });

      this.series = [];

      this.series.push(
        {
          name: "Valor de venda",
          data: linhaVenda
        },
        {
          name: "Valor de custo",
          data: linhaCusto
        }
      );
      if (this.inicio == "" || this.fim == "") {
        this.series = [];
        this.getVendas();
      }

      this.inicio = "";
      this.fim = "";
    },

    dataFormatoAmericano(data) {
      let parts = data.split("/");
      return `${parts[2]}/${parts[1]}/${parts[0]}`;
    },

    formatteDate(data) {
      let vetData = data.split(" ");
      let newFormatte = `${vetData[1]} ${vetData[2]} ${vetData[5]}`;
      let d = new Date(newFormatte);
      return [d.getFullYear(), d.getMonth() + 1, d.getDate()]
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
