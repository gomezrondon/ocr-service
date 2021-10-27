let app= Vue.createApp({
    data: function (){
        return {
            total:0.00
        }
    },
    methods:{

    }
})
app.component('login-form',{
    template: `
            <form @submit.prevent="handleSubmit">
            <h1>{{title}}</h1>
                <input id="fileuploader" type="file" name="fileuploader">
                <button>Upload</button> 
            </form>
            `,
    data(){
        return {
            title: 'File Uploader'
        }
    },
    methods: {
        handleSubmit() {
            this.sendFiles();
        },
        sendFiles() {
            let formData = new FormData();
            formData.append("file", fileuploader.files[0])

            fetch('http://localhost:8080/upload-file', {
                method: 'post',
                headers: {
                    // 'content-type': 'text/plain'
                },
                body: formData
            }).then(res => {
                // a non-200 response code
                if (!res.ok) {
                    // create error instance with HTTP status text
                    const error = new Error(res.statusText);
                    error.json = res.json();
                    throw error;
                }
                console.log(res);

            })
        }
    }
})

app.mount('#app')