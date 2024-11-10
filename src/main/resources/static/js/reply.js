console.log("reply.js loaded");

async function get1(bno) {
    console.log(bno);
    console.log(`replies/list/${bno}`);
    const result = await axios.get(`/replies/list/${bno}`);
    console.log(result);
    //return result.data;
    return result;
}

async function get2(bno) {
    console.log("get2");

}

async function getList({bno, page, size, goLast}) {
    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}});

    if (goLast) {
        const total = result.data.total;
        const lastPage = parseInt(Math.ceil(total/size));
        return getList({bno:bno, page:lastPage, size:size});

    }
    return result.data;
}

async function addReply(replyObj) {
    const response = await axios.post(`/replies/`,replyObj)
    return response.data
}

async function getReply(rno) {
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

async function modifyReply(replyObj) {

    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    return response.data
}

async function removeReply(rno) {
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}