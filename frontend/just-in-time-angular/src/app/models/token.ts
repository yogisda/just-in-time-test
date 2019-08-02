export class Token {
    token: string;

    //payload:
    role?: number;
    iss?: string;
    id?: number
    exp?: number;

    constructor(token = '') {
        this.token = token;
    }

    fill() {
        let pay = this.getPayload();
        this.role = pay.role;
        this.iss = pay.iss;
        this.id = pay.id;
        this.exp = pay.exp;
    }

    getPayload(): any {
        try {
            let payload = this.token.split('.')[1];
            return JSON.parse(atob(payload));
        } catch (error) {
            console.error(
                'Error reading Token. This might happen, if you are not logged in.'
            );
            return false;
        }
    }

    isValid(): boolean {
        if (this.token !== null) {
            if (this.token.length > 1) {
                let payload = this.getPayload();
                let now = Date.now();
                if (payload && now < payload.exp * 1000) {
                    return true;
                }
            }
        }
        return false;
    }
}
