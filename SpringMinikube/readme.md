##





### Common commands

> docker build -t spring-t14:3.0 .

> minikube service list

> minikube service localstack

> minikube ip 

> kubectl get pods --all-namespaces

> kubectl apply -f app_deployment.yml

> kubectl exec -it spring-webflux-demo-d8d5df858-56fzv /bin/bash

> kubectl logs spring-webflux-demo-64c959c4f5-spbsn

> kubectl get pods -o wide

>kubectl delete service localstack 

> DYNAMO_ENDPOINT=[http://192.168.49.2:31001](http://192.168.49.2:31001/) dynamodb-admin --open

## Code Example

### Ingress
---

namespace.yaml

```
apiVersion: v1
kind: Namespace
metadata:
  name: sample
```


ingress.yaml

```
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: days-app-ingress
  namespace: sample
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
    - http:
        paths:
          - path: /
            pathType: Prefix  
            backend:
              service:
                name: static-web-service
                port:
                  number: 80
```
How to implement ingress?

```
minikube start --driver=virtualbox

minikube addons enable ingress

kubectl apply -f namespace.yaml

kubectl apply -f deployments.yaml

kubectl apply -f services.yaml

kubectl apply -f ingress.yaml

kubectl get ingress -n sample (this may take some time to display address. this address equals minikube ip)
```

Get the minikube ip using minikube ip (let us assume it is 192.168.49.2)

Copy the hosts file located at C Drive -> Windows -> Systems32-> drivers -> etc and paste it on Desktop

In the hosts file of Desktop, add the line: 192.168.49.2 minikube-example.com and then save it.

Now copy this modified hosts file from Desktop, and paste it back in C Drive -> Windows -> Systems32-> drivers -> etc folder.

Finally, in the browser, search for minikube-example.com



### Reference:
- [Kubernetes Overview](https://kubernetes.io/docs/concepts/overview/)
  - [Major Components (Nodes,POD,etc)](https://kubernetes.io/docs/concepts/overview/components/)
- [Minikube-Getting Started](https://minikube.sigs.k8s.io/docs/start/)
- [ingress-git](https://github.com/cloudxlab/minikube-static-app/tree/main/k8s)
  - [You tube tutorial](https://www.youtube.com/watch?v=Gip-Q6AWpcY)   
