##





### Common commands

> docker build -t spring-t14:3.0 .

> minikube service list

> minikube service localstack

> kubectl get pods --all-namespaces

> kubectl apply -f app_deployment.yml

> kubectl exec -it spring-webflux-demo-d8d5df858-56fzv /bin/bash

> kubectl logs spring-webflux-demo-64c959c4f5-spbsn

> kubectl get pods -o wide

> DYNAMO_ENDPOINT=[http://192.168.49.2:31001](http://192.168.49.2:31001/) dynamodb-admin --open




### Reference:
- [Kubernetes Overview](https://kubernetes.io/docs/concepts/overview/)
  - [Major Components (Nodes,POD,etc)](https://kubernetes.io/docs/concepts/overview/components/)
- [Minikube-Getting Started](https://minikube.sigs.k8s.io/docs/start/)
