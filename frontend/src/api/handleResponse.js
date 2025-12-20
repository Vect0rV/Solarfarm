

export async function handleResponse(response) {
    let body = null;

    try {
        body = await response.json();
    } catch {}

    if (response.ok) {
        return { error: false,
            data: body
        };
    }

    if (response.status === 400) {
        console.log("400 result Body:", body);
       return { 
            error: true,
            messages: body?.messages || ["Invalid request"]
        };
    } 
    
    if (response.status === 403) {
        return {
            error: true,
            messages: ["Unauthorized request"]
        };
    } 
    
    return {
        error: true,
        messages: ["Unexpected error"]
    };

}