
#if($contextDescription && $contextDescription != '')
			<!-- $contextDescription -->
#end
			<Context path="$contextPath" docBase="$contextDocBase" $!contextAdditionalAttributes>
				<!-- Uncomment this to disable session persistence across Tomcat restarts -->
				<!-- <Manager pathname="" /> -->
#if($contextLoader && $contextLoader != '')
				$contextLoader
#end
#if($contextAdditionalElements && $contextAdditionalElements != '')
				$contextAdditionalElements
#end
			</Context>