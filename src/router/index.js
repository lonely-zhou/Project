import {
  createRouter,
  createWebHistory
} from 'vue-router'
import Login from "../components/Login.vue"
import index from "../components/index.vue"
import Register from "../components/Register.vue"
import LaunchScreen from "../components/LaunchScreen.vue"
import ShopDetail from "../components/ShopDetail.vue"
import DishesDetail from "../components/DishesDetail.vue"
import BuyPage from "../components/BuyPage.vue"
import UserAllCollection from "../components/UserAllCollection.vue"
import Search from "../components/Search.vue"
import MyPage from "../components/MyPage.vue"
import Comments from "../components/Comments.vue";
import UserAllOrders from "../components/UserAllOrders.vue";
import BuyCar from "../components/BuyCar.vue"

const routes = [{
  path: "/buyCar",
  component: BuyCar,
  name: "buyCar"
}, {
  path: "/userAllOrders",
  component: UserAllOrders,
  name: "userAllOrders"
}, {
  path: "/comments",
  component: Comments,
  name: "comments"
}, {
  path: "/myPage",
  component: MyPage,
  name: "myPage"
}, {
  path: "/search/:search",
  component: Search,
  name: "search"
}, {
  path: "/userAllCollection",
  component: UserAllCollection,
  name: "userAllCollection"
}, {
  path: "/buy/:food_id",
  component: BuyPage,
  name: "buy"
}, {
  path: "/dishesDetail/:shop_id/:food_id",
  component: DishesDetail,
  name: "dishesDetail"
}, {
  path: "/",
  component: LaunchScreen,
  name: "LaunchScreen"
}, {
  path: "/index",
  component: index,
  name: "index"
}, {
  path: "/register",
  component: Register,
  name: "register"
}, {
  path: "/login",
  component: Login,
  name: "login"
}, {
  path: "/shopDetail/:shop_id",
  component: ShopDetail,
  name: "shopDetail"
}]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router