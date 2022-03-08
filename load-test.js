import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    vus: 100,
    duration: '60s',
};

export default function () {
    const params = {
        headers: {
            'X-Correlation-Id': '02d726601bd77bef',
            'X-Request-Id': 'feb77db016627d02'
        },
    };
    const res = http.get('http://localhost:18080/factorial?number=50', params);
    check(res,
        {
            'Status is 200': (r) => r.status === 200,
            'Response is OK': (r) => r.body === '30414093201713378043612608166064768844377641568960512000000000000',
            'Same correlation ID': (r) => r.headers['X-Correlation-Id'] === '02d726601bd77bef',
            'Different request ID': (r) => r.headers['X-Request-Id'] !== 'feb77db016627d02'
        });
    sleep(0.1);
}
