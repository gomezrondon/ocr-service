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
            {{ocr_text}}
            `,
    data(){
        return {
            title: 'File Uploader',
            ocr_text:''
        }
    },
    methods: {
        handleSubmit() {
            this.sendFiles();
        },
        sendFiles() {
            let language = "eng";
            let formData = new FormData();
            formData.append("file", fileuploader.files[0])

            fetch('http://localhost:8080/upload-file/'+language, {
            //     fetch('https://ocr-service-vilz3brbpa-uc.a.run.app/upload-file/'+language, {

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
                // console.log(res.text());
                return res.text();
            }).then(res => {
                this.ocr_text = res;
            })
        }
    }
})

app.mount('#app')