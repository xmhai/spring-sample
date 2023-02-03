$JSON = Get-Content application.json | Out-String | ConvertFrom-Json
$lineNo = 0
$userId = ""
foreach($line in Get-Content request.txt) {
	$lineNo++;
	if($lineNo -eq 1) {
		$userId = $line
		Write-Host "UserId: $userId"
		continue;
	}
	
    if($line.StartsWith("Add:")){
		$appToAdd = $line.Substring(4, $line.Length-4).Trim()
		#Write-Host "Application to add: " $appToAdd
		
		# Get Application UUID
		$appIdFound = $false
		foreach ($application in $JSON.applications)
		{
			if($application.name -eq $appToAdd) {
				$appIdFound = $true
				$appId = $application.id
				Write-Host "curl http://nexuspru.com/application/$appId/role/2349458578475874389/user/$userId"
				cmd.exe /c "curl http://nexuspru.com/application/$appId/role/2349458578475874389/user/$userId"
				break;
			}
		}
		
		if (!$appIdFound) {
			Write-Host "WARNING: $appToAdd not found in application list json"
		}
    }
}

pause

