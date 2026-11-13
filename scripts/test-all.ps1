$ErrorActionPreference = 'Stop'
$repositoryRoot = Split-Path -Parent $PSScriptRoot
$parentPom = Join-Path $repositoryRoot 'build\service-parent\pom.xml'

if (Test-Path $parentPom) {
    Write-Host 'Installing shared service parent POM...'
    & (Join-Path $repositoryRoot 'backend\auth-service\mvnw.cmd') -f $parentPom clean install -DskipTests
    if ($LASTEXITCODE -ne 0) {
        throw "Shared service parent POM install failed with exit code $LASTEXITCODE"
    }
}

$backendPath = Join-Path $repositoryRoot 'backend'
$serviceProjects = Get-ChildItem -Path $backendPath -Directory |
    Where-Object { Test-Path (Join-Path $_.FullName 'pom.xml') } |
    Sort-Object Name

if ($serviceProjects.Count -eq 0) {
    throw "No Maven service projects found under $backendPath"
}

foreach ($service in $serviceProjects) {
    Write-Host "Testing $($service.Name)..."
    Push-Location $service.FullName
    try {
        & .\mvnw.cmd clean test
        if ($LASTEXITCODE -ne 0) {
            throw "Tests failed for $($service.Name) with exit code $LASTEXITCODE"
        }
    }
    finally {
        Pop-Location
    }
}

Write-Host "All $($serviceProjects.Count) backend service projects passed."
