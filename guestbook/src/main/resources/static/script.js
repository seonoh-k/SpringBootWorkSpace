var currentPage = 1;
var isFetch = false;
var hasMore = true;

let root = document.getElementById('root');

async function fetchData() {
    isFetch = true;
    let response = await fetch(`https://jsonplaceholder.typicode.com/posts?_page=${currentPage}`);
    let data = await response.json();
    console.log(data);

    isFetch = false;

    if (data.length === 0) {
        hasMore = false;
        return
    }

    var count = 1;

    for(let post of data) {
        let div = document.createElement('div');
        div.innerHTML = `<span>${count}</span><h2>${post.title}</h2><p>${post.body}</p>`
        root.appendChild(div);
        count++;
    }
    currentPage++;
}


window.addEventListener('scroll', () => {

    if (isFetch || !hasMore) {
        return
    }

    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        fetchData();
    }

})

fetchData();