var vue = new Vue({
    el: '#app',
    data: {

        // 搜索栏时间
        pickerOptions: {
            disabledDate(time) {
                return time.getTime() > Date.now();
            },
            shortcuts: [{
                text: '今天',
                onClick(picker) {
                    picker.$emit('pick', new Date());
                }
            }, {
                text: '昨天',
                onClick(picker) {
                    const date = new Date();
                    date.setTime(date.getTime() - 3600 * 1000 * 24);
                    picker.$emit('pick', date);
                }
            }, {
                text: '一周前',
                onClick(picker) {
                    const date = new Date();
                    date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', date);
                }
            }]
        },

        // 满意度调查
        value1: null,
        value2: 3.6,

        // 公告
        drawer: false,

        // 顶部菜单
        activeName: 'first',

        // 走马灯图片
        imgwrap: [
            {url: ("../image/lf-1.jpg")},
            {url: ("../image/lf-2.jpg")},
            {url: ("../image/lf-3.jpg")},
            {url: ("../image/lf-4.jpg")},
            {url: ("../image/lf-5.jpg")}
        ],

        text: '',

        radio: 2,

        dataList: [],//当前页要展示的列表数据
        dialogFormVisible: false,//添加表单是否可见
        dialogFormVisible4Edit: false,//编辑表单是否可见
        formData: {},//表单数据
        rules: {//校验规则
            res: [{required: true, message: '必填项', trigger: 'blur'}],
            time: [{required: true, message: '必填项', trigger: 'blur'}],
            description: [{required: true, message: '必填项', trigger: 'blur'}],
            number: [{required: true, message: '必填项', trigger: 'blur'}],
            state: [{required: true, message: '必填项', trigger: 'blur'}],
        },
        pagination: {//分页相关模型数据
            currentPage: 1,//当前页码
            pageSize: 10,//每页显示的记录数
            total: 0,//总记录数
            res: "",//条件查询模型
            time: "",
            description: "",
            createtime: "",
            state: ""
        },

        optionsstate: [{
            value: '丢',
            label: '丢'
        }, {
            value: '捡',
            label: '捡'
        }],
        value: ''
    },

    //钩子函数，VUE对象初始化完成后自动执行
    created() {
        //调用查询全部数据的操作
        this.getAll();
    },

    methods: {

        formatgmtState(state) {
            if (state === "捡") {
                return '捡'
            } else if (state === "丢") {
                return '丢'
            } else {
                return '错误状态'
            }
        },

        //顶部菜单
        handleClick(tab, event) {
            console.log(tab, event);
        },

        tableRowClassName({row, rowIndex}) {
            let index = rowIndex;
            if (index % 2 == 0) {
                return 'warning-row'
            }
        },
        // 重置搜索表单
        reset() {
            location.reload();
        },

        // 列表
        getAll() {

            param = "?res=" + this.pagination.res;
            param += "&time=" + this.pagination.time;
            param += "&description=" + this.pagination.description;
            param += "&state=" + this.pagination.state;

            // 发送异步请求 分页
            axios.get("/pickup/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then((res) => {
                // console.log(res.data);
                this.pagination.pageSize = res.data.data.size;
                this.pagination.currentPage = res.data.data.current;
                this.pagination.total = res.data.data.total;
                this.dataList = res.data.data.records;
            })
        },


        //切换页码
        handleCurrentChange(currentPage) {
            //修改页码值为当前选中的页码值
            this.pagination.currentPage = currentPage;
            //执行查询
            this.getAll();
        },

        //弹出添加窗口
        handleCreate() {
            this.dialogFormVisible = true;
            this.resetForm();
        },

        //重置表单
        resetForm() {
            this.formData = {};
        },

        //添加
        handleAdd() {
            axios.post("/pickup", this.formData).then((res) => {
                // 判断当前操作是否成功
                if (res.data.flag) {
                    // 关闭弹窗
                    this.dialogFormVisible = false;
                    this.$message.success(res.data.msg)
                } else {
                    this.$message.error(res.data.msg)
                }
            }).finally(() => {
                // 重新加载
                this.getAll();
            })
        },

        //取消
        cancel() {
            this.dialogFormVisible = false;
            this.dialogFormVisible4Edit = false;
            this.$message.info("已取消当前操作");
        },

        // 删除
        handleDelete(row) {
            this.$confirm("此操作将删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
                axios.delete("/pickup/" + row.id).then((res) => {
                    if (res.data.flag) {
                        this.$message.success("删除成功");
                    } else {
                        this.$message.error("删除失败");
                    }
                }).finally(() => {
                    this.getAll();
                })
            }).catch(() => {
                this.$message.info("已取消当前操作");
            })
        },

        //弹出编辑窗口
        handleUpdate(row) {
            axios.get("/pickup/" + row.id).then((res) => {
                if (res.data.flag && res.data.data != null) {
                    this.dialogFormVisible4Edit = true;
                    this.formData = res.data.data;
                } else {
                    this.$message.error("数据同步失败，自动刷新")
                }

            }).finally(() => {
                // 重新加载
                this.getAll();
            })
        },

        //修改
        handleEdit() {
            axios.put("/pickup", this.formData).then((res) => {
                // 判断当前操作是否成功
                if (res.data.flag) {
                    // 关闭弹窗
                    this.dialogFormVisible4Edit = false;
                    this.$message.success("修改成功")
                } else {
                    this.$message.error("修改失败")
                }
            }).finally(() => {
                // 重新加载
                this.getAll();
            })
        },

    }
})