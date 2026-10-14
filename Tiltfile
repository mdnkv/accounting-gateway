# build
custom_build(
    ref = 'accounting-gateway',
    command = './gradlew bootBuildImage --imageName $EXPECTED_REF',
    deps = ['build.gradle', 'src']
)

# deploy app
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# manage
k8s_resource('accounting-gateway', port_forwards=['8000:8000'])