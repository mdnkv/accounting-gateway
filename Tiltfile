# build
custom_build(
    ref = 'accounting-gateway',
    command = './gradlew bootBuildImage --imageName $EXPECTED_REF',
    deps = ['build.gradle', 'src']
)

# redis
k8s_yaml(['k8s/redis.yml'])
k8s_resource('accounting-gateway-redis', port_forwards=['6379:6379'])

# deploy app
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# manage
k8s_resource('accounting-gateway', resource_deps=['accounting-gateway-redis'])
k8s_resource('accounting-gateway', port_forwards=['8000:8000'])