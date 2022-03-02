import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 100,
    duration: '60s',
};

export default function () {
    const res = http.get('http://localhost:28080/factorial?number=50');
    check(res, 
    { 
        'status was 200': (r) => r.status == 200,
        'response is OK': (r) => r.body == '30414093201713378043612608166064768844377641568960512000000000000'
    });
    sleep(0.5);
}
