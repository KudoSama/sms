webpackJsonp([1],{"/V7C":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=a("7+uW"),l={name:"modifyBatch",data:function(){return{form:{batchId:"",batchDatestart:"",batchDateend:""}}},methods:{onSubmit:function(){console.log("submit!")},modifyBatch:function(){var t={batchId:this.form.batchId,batchDatestart:this.form.batchDatestart,batchDateend:this.form.batchDateend};this.$axios.post("api/batch/modify",t).then(function(t){console.info(t.data),null!==t.data&&!0===t.data.status?o.default.prototype.$message.success(t.data.message):o.default.prototype.$message.error(t.data.message)})}}},r={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("h1",[t._v("修改衣物批次")]),t._v(" "),a("el-form",{ref:"form",attrs:{model:t.form,"label-width":"100px"}},[a("el-col",{attrs:{span:10}},[a("el-form-item",{attrs:{label:"批次号"}},[a("el-input",{model:{value:t.form.batchId,callback:function(e){t.$set(t.form,"batchId",e)},expression:"form.batchId"}})],1)],1),t._v(" "),a("br"),a("br"),a("br"),a("br"),a("br"),t._v(" "),a("el-form-item",{attrs:{label:"批次时间"}},[a("el-col",{attrs:{span:5}},[a("el-date-picker",{staticStyle:{width:"100%"},attrs:{type:"date",placeholder:"选择开始日期"},model:{value:t.form.batchDatestart,callback:function(e){t.$set(t.form,"batchDatestart",e)},expression:"form.batchDatestart"}})],1),t._v(" "),a("el-col",{staticClass:"line",attrs:{span:.1}},[t._v("-")]),t._v(" "),a("el-col",{attrs:{span:5}},[a("el-date-picker",{staticStyle:{width:"100%"},attrs:{placeholder:"选择结束日期"},model:{value:t.form.batchDateend,callback:function(e){t.$set(t.form,"batchDateend",e)},expression:"form.batchDateend"}})],1),t._v(" "),a("br"),a("br"),t._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:t.modifyBatch}},[t._v("修改")]),t._v(" "),a("el-button",[t._v("取消")])],1)],1)],1)],1)},staticRenderFns:[]};var s=a("VU/8")(l,r,!1,function(t){a("6bt/")},"data-v-39576e08",null);e.default=s.exports},"0Dh5":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o={name:"mainpage",data:function(){return{imgwrap:[{url:a("PBBS")},{url:a("vc/q")},{url:a("OW58")}]}}},l={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"mainpagebody"},[this._m(0),this._v(" "),e("br"),e("br"),e("br"),e("br"),this._v(" "),[e("el-carousel",{attrs:{"indicator-position":"outside"}},this._l(this.imgwrap,function(t){return e("el-carousel-item",{key:t.url},[e("img",{attrs:{src:t.url}})])}),1)]],2)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",[e("h1",[this._v("欢迎使用寒衣补助系统")])])}]};var r=a("VU/8")(o,l,!1,function(t){a("xBW6")},null,null);e.default=r.exports},"6bt/":function(t,e){},"7Vwd":function(t,e){},AUTZ:function(t,e){},Btm0:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o={name:"schoolgetCloth",data:function(){return{clotlist:[],radio:"1",input:""}},methods:{clothlist:function(){var t=this;this.$axios.get("/api/cloth/getClothByBatchId",{params:{batchId:this.input,pageNo:1,pageSize:10}}).then(function(e){console.info(e),t.clotlist=e.data.data.records})}}},l={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("h1",[t._v("查询衣物")]),t._v(" "),a("h2",[t._v("请输入批次号")]),t._v(" "),a("el-input",{attrs:{placeholder:"请输入批次号"},model:{value:t.input,callback:function(e){t.input=e},expression:"input"}}),t._v(" "),a("br"),t._v(" "),a("h3",[t._v("查询结果")]),t._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.clotlist}},[a("el-table-column",{attrs:{prop:"clothId",label:"服装id"}}),t._v(" "),a("el-table-column",{attrs:{prop:"clothName",label:"服装名"}}),t._v(" "),a("el-table-column",{attrs:{prop:"gender",label:"性别"}}),t._v(" "),a("el-table-column",{attrs:{prop:"batchId",label:"批次号"}})],1),t._v(" "),a("el-button",{on:{click:t.clothlist}},[t._v("查询")])],1)},staticRenderFns:[]};var r=a("VU/8")(o,l,!1,function(t){a("YWLy")},"data-v-515da495",null);e.default=r.exports},BwRb:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=a("dZUF"),l=a.n(o);for(var r in o)"default"!==r&&function(t){a.d(e,t,function(){return o[t]})}(r);var s=a("EVN7");var n=function(t){a("WrvQ")},i=a("VU/8")(l.a,s.a,!1,n,"data-v-394926c6",null);e.default=i.exports},EUVb:function(t,e){},EVN7:function(t,e,a){"use strict";var o={render:function(){var t=this.$createElement;return(this._self._c||t)("div")},staticRenderFns:[]};e.a=o},"Gtq/":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=a("7+uW"),l={name:"addimg",data:function(){return{imageUrl:"",clothId:4}},methods:{uploadimg:function(){var t={clothId:this.clothId,clothImg:this.imageUrl};console.info(t),this.$axios.post("/api/clothImg/upload",t).then(function(t){null!==t?o.default.prototype.$message.success(t.data.message):o.default.prototype.$message.error(t.data.message)})},handleAvatarSuccess:function(t,e){this.imageUrl=t.url,console.info(this.imageUrl)},beforeAvatarUpload:function(t){var e="image/jpeg"===t.type,a=t.size/1024/1024<5;return e||this.$message.error("上传图片只能是 JPG 格式!"),a||this.$message.error("上传图片大小不能超过 5MB!"),e&&a}}},r={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[t._m(0),t._v(" "),a("div",[a("br"),t._v(" "),a("h3",[t._v("请输入要插入图像的衣物id：")]),t._v(" "),a("el-input",{attrs:{placeholder:"请输入衣物id"},model:{value:t.clothId,callback:function(e){t.clothId=e},expression:"clothId"}}),t._v(" "),a("div",[a("el-upload",{staticClass:"avatar-uploader",attrs:{action:"/api/file/upload","show-file-list":!1,"auto-upload":!0,"on-success":t.handleAvatarSuccess,"before-upload":t.beforeAvatarUpload}},[t.imageUrl?a("img",{staticClass:"avatar",attrs:{src:t.imageUrl}}):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"})]),t._v(" "),a("el-button",{on:{click:t.uploadimg}},[t._v(" 提交 ")])],1)],1)])},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",[e("h1",[this._v("图像测试界面")])])}]};var s=a("VU/8")(l,r,!1,function(t){a("AUTZ")},"data-v-4521be02",null);e.default=s.exports},"I+f9":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=a("7+uW"),l={name:"addclothByid",data:function(){return{form:{clothId:"",clothName:"",gender:"",batchId:""}}},methods:{onSubmit:function(){console.log("submit!")},addClothByid:function(){var t={clothId:this.form.clothId,clothName:this.form.clothName,gender:this.form.gender,batchId:this.form.batchId};this.$axios.post("api/cloth/add",t).then(function(e){console.info(t),console.info(e.data),null!==e.data&&!0===e.data.status?o.default.prototype.$message.success(e.data.message):o.default.prototype.$message.error(e.data.message)})}}},r={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("h1",[t._v("添加衣物")]),t._v(" "),a("el-form",{ref:"form",attrs:{model:t.form,"label-width":"100px"}},[a("el-col",{attrs:{span:10}},[a("el-form-item",{attrs:{label:"衣物id"}},[a("el-input",{model:{value:t.form.clothId,callback:function(e){t.$set(t.form,"clothId",e)},expression:"form.clothId"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"衣物名"}},[a("el-input",{model:{value:t.form.clothName,callback:function(e){t.$set(t.form,"clothName",e)},expression:"form.clothName"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"批次号"}},[a("el-input",{model:{value:t.form.batchId,callback:function(e){t.$set(t.form,"batchId",e)},expression:"form.batchId"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"性别"}},[a("el-select",{attrs:{placeholder:"请选择性别"},model:{value:t.form.gender,callback:function(e){t.$set(t.form,"gender",e)},expression:"form.gender"}},[a("el-option",{attrs:{label:"男",value:"男"}}),t._v(" "),a("el-option",{attrs:{label:"女",value:"女"}})],1)],1)],1),t._v(" "),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),t._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:t.addClothByid}},[t._v("立即添加")]),t._v(" "),a("el-button",[t._v("取消")])],1)],1)],1)},staticRenderFns:[]};var s=a("VU/8")(l,r,!1,function(t){a("7Vwd")},"data-v-746711d2",null);e.default=s.exports},OW58:function(t,e,a){t.exports=a.p+"static/img/pic3.3d6168b.jpg"},PBBS:function(t,e,a){t.exports=a.p+"static/img/pic1.3041fe6.jpg"},"Sd/4":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=a("7+uW"),l={name:"addclothByid",data:function(){return{form:{clothId:"",clothSize:""},value1:!1,value2:!1,value3:!1,value4:!1}},methods:{onSubmit:function(){console.log("submit!")},addClothByid:function(){if(!0===this.value1){var t={clothId:this.form.clothId,clothSize:"S"};this.$axios.post("/api/clothSize/add",t).then(function(e){console.info(t),console.info(e.data),null!==e.data&&!0===e.data.status?o.default.prototype.$message.success(e.data.message):o.default.prototype.$message.error(e.data.message)})}if(!0===this.value2){var e={clothId:this.form.clothId,clothSize:"M"};this.$axios.post("/api/clothSize/add",e).then(function(t){console.info(e),console.info(t.data),null!==t.data&&!0===t.data.status?o.default.prototype.$message.success(t.data.message):o.default.prototype.$message.error(t.data.message)})}if(!0===this.value3){var a={clothId:this.form.clothId,clothSize:"L"};this.$axios.post("/api/clothSize/add",a).then(function(t){console.info(a),console.info(t.data),null!==t.data&&!0===t.data.status?o.default.prototype.$message.success(t.data.message):o.default.prototype.$message.error(t.data.message)})}if(!0===this.value4){var l={clothId:this.form.clothId,clothSize:"XL"};this.$axios.post("/api/clothSize/add",l).then(function(t){console.info(l),console.info(t.data),null!==t.data&&!0===t.data.status?o.default.prototype.$message.success(t.data.message):o.default.prototype.$message.error(t.data.message)})}}}},r={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("h1",[t._v("添加衣物尺码")]),t._v(" "),a("el-form",{ref:"form",attrs:{model:t.form,"label-width":"100px"}},[a("el-col",{attrs:{span:10}},[a("el-form-item",{attrs:{label:"衣物id"}},[a("el-input",{model:{value:t.form.clothId,callback:function(e){t.$set(t.form,"clothId",e)},expression:"form.clothId"}})],1),t._v(" "),a("h3",[t._v("\n                可选尺码\n            ")]),t._v(" "),a("el-switch",{attrs:{"inactive-text":"S "},model:{value:t.value1,callback:function(e){t.value1=e},expression:"value1"}}),t._v(" "),a("el-switch",{attrs:{"inactive-text":"M "},model:{value:t.value2,callback:function(e){t.value2=e},expression:"value2"}}),t._v(" "),a("el-switch",{attrs:{"inactive-text":"L "},model:{value:t.value3,callback:function(e){t.value3=e},expression:"value3"}}),t._v(" "),a("el-switch",{attrs:{"inactive-text":"XL "},model:{value:t.value4,callback:function(e){t.value4=e},expression:"value4"}})],1),t._v(" "),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),a("br"),t._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:t.addClothByid}},[t._v("立即添加")]),t._v(" "),a("el-button",[t._v("取消")])],1)],1)],1)},staticRenderFns:[]};var s=a("VU/8")(l,r,!1,function(t){a("EUVb")},"data-v-9fb04c2c",null);e.default=s.exports},WrvQ:function(t,e){},YWLy:function(t,e){},dZUF:function(t,e){},"ejP+":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=a("7+uW"),l={name:"addBatch",data:function(){return{form:{batchId:"",batchDatestart:"",batchDateend:""}}},methods:{onSubmit:function(){console.log("submit!")},addBatch:function(){var t={batchId:this.form.batchId,batchDatestart:this.form.batchDatestart,batchDateend:this.form.batchDateend};this.$axios.post("api/batch/add",t).then(function(t){console.info(t.data),null!==t.data&&!0===t.data.status?o.default.prototype.$message.success(t.data.message):o.default.prototype.$message.error(t.data.message)})}}},r={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("h1",[t._v("添加衣物批次")]),t._v(" "),a("el-form",{ref:"form",attrs:{model:t.form,"label-width":"100px"}},[a("el-col",{attrs:{span:10}},[a("el-form-item",{attrs:{label:"批次号"}},[a("el-input",{model:{value:t.form.batchId,callback:function(e){t.$set(t.form,"batchId",e)},expression:"form.batchId"}})],1)],1),t._v(" "),a("br"),a("br"),a("br"),a("br"),a("br"),t._v(" "),a("el-form-item",{attrs:{label:"批次时间"}},[a("el-col",{attrs:{span:5}},[a("el-date-picker",{staticStyle:{width:"100%"},attrs:{type:"date",placeholder:"选择开始日期"},model:{value:t.form.batchDatestart,callback:function(e){t.$set(t.form,"batchDatestart",e)},expression:"form.batchDatestart"}})],1),t._v(" "),a("el-col",{staticClass:"line",attrs:{span:.1}},[t._v("-")]),t._v(" "),a("el-col",{attrs:{span:5}},[a("el-date-picker",{staticStyle:{width:"100%"},attrs:{placeholder:"选择结束日期"},model:{value:t.form.batchDateend,callback:function(e){t.$set(t.form,"batchDateend",e)},expression:"form.batchDateend"}})],1),t._v(" "),a("br"),a("br"),t._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:t.addBatch}},[t._v("添加")]),t._v(" "),a("el-button",[t._v("取消")])],1)],1)],1)],1)},staticRenderFns:[]};var s=a("VU/8")(l,r,!1,function(t){a("fEfk")},"data-v-1d04dad5",null);e.default=s.exports},fEfk:function(t,e){},jbNu:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o={name:"second",data:function(){return{activeName:"1",schoolList:[{name:"A学院",id:0},{name:"B学院",id:1},{name:"C学院",id:2}]}},methods:{haha:function(t){alert(t)}}},l={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[t._v("\n  学校登陆后显示界面"),a("br"),t._v(" "),a("el-card",{staticClass:"crumbs-card"},[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",{attrs:{to:{path:"/main/"}}},[t._v("首页")]),t._v(" "),a("el-breadcrumb-item",{attrs:{to:{path:"/main/first"}}},[t._v("一级菜单1")])],1)],1)]),t._v(" "),a("el-card",{staticClass:"container"},[a("el-tabs",{on:{"tab-click":t.handleClick},model:{value:t.activeName,callback:function(e){t.activeName=e},expression:"activeName"}},t._l(t.schoolList,function(e,o){return a("el-tab-pane",{key:o,staticStyle:{margin:"0px"},attrs:{label:e.name,name:e.id}},[a("el-row",{staticStyle:{"border-bottom":"1px solid black"}},[a("el-col",{staticStyle:{"border-right":"1px solid black"},attrs:{span:12}},[a("el-row",[a("p",{staticStyle:{"border-left":"5px solid blue","padding-left":"5px","font-size":"20px"}},[t._v("状态类型")])]),t._v(" "),a("el-row",[a("el-col",{staticStyle:{"margin-top":"20px"}},[t._v("正常打卡时间段：")]),t._v(" "),a("el-col",{staticStyle:{"margin-top":"20px"}},[t._v("晚归打卡时间段：")])],1)],1),t._v(" "),a("el-col",{staticStyle:{"padding-left":"30px"},attrs:{span:12}},[a("p",{staticStyle:{"border-left":"5px solid blue","padding-left":"5px","font-size":"20px"}},[t._v("系统规则说明")]),t._v(" "),a("p",{staticStyle:{"padding-left":"5px"}},[t._v("学生当天有考勤：超过当天24：00打卡的学生自动视为(未归且缺卡)；\n              若学生第二天有/无考勤，则24：00至8：00学生打卡状态为(未归)，过8：00之后学生\n              打卡状态为（未到时间），直至正常打卡时间段开始；")])])],1)],1)}),1)],1)],1)},staticRenderFns:[]},r=a("VU/8")(o,l,!1,null,null,null);e.default=r.exports},"vc/q":function(t,e,a){t.exports=a.p+"static/img/pic2.5f2a47c.jpg"},xBW6:function(t,e){}});