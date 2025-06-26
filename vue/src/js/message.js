import message from "../components/message.vue"
import { h, render } from "vue"
function setMessage(setData,container){
    const Vnode = h(message,{ props: { message: setData } })
    //document
    render(Vnode,container)
}

export{setMessage}