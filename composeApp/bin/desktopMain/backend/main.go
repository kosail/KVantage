package main

import (
	"bufio"
	"flag"
	"fmt"
	"os"
	"os/exec"
	"strconv"
	"strings"
)



//  ██████╗ ██████╗ ███╗   ██╗███████╗████████╗    ███████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
// ██╔════╝██╔═══██╗████╗  ██║██╔════╝╚══██╔══╝    ██╔════╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
// ██║     ██║   ██║██╔██╗ ██║███████╗   ██║       ███████╗█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
// ██║     ██║   ██║██║╚██╗██║╚════██║   ██║       ╚════██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
// ╚██████╗╚██████╔╝██║ ╚████║███████║   ██║       ███████║███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
//  ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝   ╚═╝       ╚══════╝╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝

// ANSI color and style codes
const (
	Reset      = "\033[0m"
	Bold       = "\033[1m"
	FgCyan     = "\033[36m"
	FgGreen    = "\033[32m"
	FgYellow   = "\033[33m"
	FgRed      = "\033[31m"
	FgBlue     = "\033[34m"
	//FgMagenta  = "\033[35m"
)

// Do not modify these values unless you are absolutely sure of it, and you know what you are doing.
// These values are the actual values that we will be writing into the ACPI call interface
const (
	// AcpiCallPath AFAIK this path is the same across all distros, but better to no hardcode it
	AcpiCallPath = "/proc/acpi/call"

	// GET status from all options
	GetBattConservationStatus   =  "\\_SB.PCI0.LPC0.EC0.BTSM"
	GetRapidChargeStatus     = "\\_SB.PCI0.LPC0.EC0.QCHO"
	GetPerformanceModeStatus = "\\_SB.PCI0.LPC0.EC0.SPMO"

	// SET Battery Conservation Mode
	SetBattConservationOn  = "\\_SB.PCI0.LPC0.EC0.VPC0.SBMC 0x03"
	SetBattConservationOff = "\\_SB.PCI0.LPC0.EC0.VPC0.SBMC 0x05"

	// SET Rapid Charge
	SetRapidChargeOn  = "\\_SB.PCI0.LPC0.EC0.VPC0.SBMC 0x07"
	SetRapidChargeOff = "\\_SB.PCI0.LPC0.EC0.VPC0.SBMC 0x08"

	// SET Performance Mode
	SetPerformanceModeIntelligentCooling = "\\_SB.PCI0.LPC0.EC0.VPC0.DYTC 0x000FB001"
	SetPerformanceModeExtremePerformance = "\\_SB.PCI0.LPC0.EC0.VPC0.DYTC 0x0012B001"
	SetPerformanceModePowerSaving        = "\\_SB.PCI0.LPC0.EC0.VPC0.DYTC 0x0013B001"
)


// ███╗   ███╗ █████╗ ██╗███╗   ██╗    ███████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
// ████╗ ████║██╔══██╗██║████╗  ██║    ██╔════╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
// ██╔████╔██║███████║██║██╔██╗ ██║    ███████╗█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
// ██║╚██╔╝██║██╔══██║██║██║╚██╗██║    ╚════██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
// ██║ ╚═╝ ██║██║  ██║██║██║ ╚████║    ███████║███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
// ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝    ╚══════╝╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝



func main() {
	// Define a boolean flag "-h" or "--help"
	help := flag.Bool("h", false, "Show help message")
	flag.Usage = helpCommand
	flag.Parse()

	// If -h is passed, print help and exit
	if *help {
		flag.Usage()
		return
	}

	// If no flag was passed, continue executing the program. Now it's time to check if root access
	if os.Getegid() != 0 {
		fmt.Printf("%s> Not running as root. Trying to escalate using pkexec...%s\n", FgYellow, Reset)
		err := escalateWithPkexec()

		if err != nil {
			fmt.Printf("Failed to escalate privileges: %v\n", err)
			os.Exit(1)
		}
	}

	if os.Getegid() == 0 {
		fmt.Printf("%s> Root privileges confirmed. Executing as root.%s\n", FgGreen, Reset)
		fmt.Printf("%s> KvanD initialized. Launching sentinel signal to frontend:%s\n", FgBlue, Reset)
		fmt.Println("READY")
		os.Stdout.Sync() // Flush the buffer

		scanner := bufio.NewScanner(os.Stdin)
		for scanner.Scan() {
			line := scanner.Text()
			words := strings.Split(line, " ")

			if len(words) < 2 || len(words) > 3 {
				fmt.Printf("%sInvalid command format. Last line was ignored.%s\n", FgYellow, Reset)
				continue
			}

			parseCommand(words)
		}

		if err := scanner.Err(); err != nil {
			// Goland yells that I should handle the possible error by Fprintln. But tbh, I'm an absolute novice in Go.
			// I have no idea how to do it gracefully without adding unnecessary code, so I will just leave it like that.
			fmt.Fprintln(os.Stderr, "Error reading input:", err)
		}
	}
}

func helpCommand() {
	fmt.Printf("%s%sKvantage Battery Daemon v1.0%s\n", Bold, FgCyan, Reset)
	fmt.Println()

	var builder strings.Builder
	builder.WriteString("This program is not intended to be run independently, but instead as a part of KVantage, ")
	builder.WriteString("a minimal control center for Lenovo laptops on Linux (https://github.com/kosail/KVantage).")
	builder.WriteString("\n\n")
	builder.WriteString("This program reads lines from stdin until it receives an EOF (CTRL + D), ")
	builder.WriteString("splits each line by spaces, and perform the indicated action based on commands that ")
	builder.WriteString("match the tokens extracted from the split string.")
	builder.WriteString("\n\n")
	builder.WriteString(FgYellow)  // Change to yellow for warning
	builder.WriteString("This daemon needs to be run as administrator as it cannot perform IO to the ACPI call ")
	builder.WriteString("interface without it. If not ran as administrator, it will try to escalate itself ")
	builder.WriteString("using pkexec.")
	builder.WriteString(Reset)  // Reset color at the end

	fmt.Println(builder.String())
	fmt.Printf("\n%sOptions:%s\n", Bold, Reset)
	fmt.Printf("  %s-h      Show this help message%s\n", FgGreen, Reset)
}

func escalateWithPkexec() error {
	// Get the current executable path
	exe, err := os.Executable()
	if err != nil {
		return fmt.Errorf("%s> Failed to get executable path: %s\n%v", FgRed, Reset, err)
	}

	// Run the same program with pkexec
	cmd := exec.Command("pkexec", exe)
	cmd.Stdin = os.Stdin
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr

	return cmd.Run()
}

func parseCommand(tokens []string) {
	if !(tokens[0] == "get" && len(tokens) == 2) && !(tokens[0] == "set" && len(tokens) == 3) {
		var builder strings.Builder
		builder.WriteString(FgYellow + "Invalid " + tokens[0] + " command. Available options: \n")
		builder.WriteString("\t" + "get [OPTION]" + "\n")
		builder.WriteString("\t" + "set [OPTION] [MODE]" + "\n")
		builder.WriteString(Reset)

		fmt.Printf(builder.String())
		return
	}

	if tokens[0] == "get" {
		switch tokens[1] {
		case "performance":
			getStatus(GetPerformanceModeStatus)
		case "conservation":
			getStatus(GetBattConservationStatus)
		case "rapid":
			getStatus(GetRapidChargeStatus)
		default:
			var builder strings.Builder
			builder.WriteString(FgYellow + "Invalid get option. Available get modes: " + "\n")
			builder.WriteString("\t" + "get [performance, conservation, rapid]" + "\n")
			builder.WriteString(Reset)

			fmt.Printf(builder.String())
			return
		}
	}

	if tokens[0] == "set" {
		option, err := strconv.Atoi(tokens[2])

		if err != nil || option < 0 || option > 3 {
			var builder strings.Builder
			builder.WriteString(FgYellow + "Invalid set option. Available set modes:" + "\n")
			builder.WriteString("\t" + "performance [0, 1 ,2]" + "\n")
			builder.WriteString("\t" + "conservation [0, 1]" + "\n")
			builder.WriteString("\t" + "rapid [0, 1]" + "\n")
			builder.WriteString(Reset)

			fmt.Printf(builder.String())
			return
		}

		switch tokens[1] {
		case "performance":
			setPerformanceProfile(option)

		case "conservation":
			setConservation(option)

		case "rapid":
			setRapidCharge(option)

		default:
			var builder strings.Builder
			builder.WriteString(FgYellow + "Invalid set option. Available set modes: " + "\n")
			builder.WriteString("\t" + "set [performance, conservation, rapid] [MODE]" + "\n")
			builder.WriteString(Reset)

			fmt.Printf(builder.String())
			return
		}
	}

}


//  █████╗  ██████╗██████╗ ██╗     ██████╗ █████╗ ██╗     ██╗     ███████╗
// ██╔══██╗██╔════╝██╔══██╗██║    ██╔════╝██╔══██╗██║     ██║     ██╔════╝
// ███████║██║     ██████╔╝██║    ██║     ███████║██║     ██║     ███████╗
// ██╔══██║██║     ██╔═══╝ ██║    ██║     ██╔══██║██║     ██║     ╚════██║
// ██║  ██║╚██████╗██║     ██║    ╚██████╗██║  ██║███████╗███████╗███████║
// ╚═╝  ╚═╝ ╚═════╝╚═╝     ╚═╝     ╚═════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝
// Important note:
// ACPI is not as fast as I thought. When I need to check the current setting of a device, ACPI is blazing fast from whe
// I write the request and by when the response is available. Both Write and Read operations can be done sequentially.
// However, when doing writes to change the status or behavior of the hardware... well, now here is the issue.
//
// It takes around 1 second from when I write the new setting to the call interface, to when the setting is set and the
// call interface returns the new value as a confirmation of the change being successful.
//
// Due to this limitation, is not possible to perform a read call right away after a setting has been written.
// Instead, I will have the front end to manually call a read operation from the backend after it has requested a write operation.

/*
	#########################
	## About writeAcpiCall ##
	#########################

	When we want to write a setting to the ACPI interface, we don't get any feedback. So, the GUI doesn't know the result
	of the operation and hangs waiting for something to read. That's why we have a feedback parameter.
	Example:
		> set performance 0
		OK

	When we just want to check the status (the current value) of a setting, we DO care for what ACPI returns.
	We want to print that returned value, that so the GUI catches it up and updates itself accordingly.
	The GUI expects only 1 value, but if we always print OK at writeAcpiCall operations, this will be printed:
		> get conservation
		OK
		0x1

	The GUI will catch "OK" instead of the actual value, and everything messes up from the frontend side.

	I know it is a long explanation, but TL:DR we don't need feedback when checking values, just when writing.
*/
func writeAcpiCall(command string, feedback bool) {
	// Open the file with write permissions
	file, err := os.OpenFile(AcpiCallPath, os.O_WRONLY, 0)
	if err != nil {
		fmt.Printf("%sError: failed to open ACPI call interface. Information about the error:%s\n %v\n", FgRed, Reset, err)
		return
	}
	defer file.Close()

	// Write the command
	_, err = file.WriteString(command)
	if err != nil {
		fmt.Printf("%sError: failed to write to ACPI call interface. Information about the error:%s\n %v\n", FgRed, Reset, err)
		return
	}

	if feedback {
		fmt.Println("OK")
		os.Stdout.Sync()
	}
}

func readAcpiCall() {
	// Open the file with read permissions
	file, err := os.OpenFile(AcpiCallPath, os.O_RDONLY, 0)
	if err != nil {
		fmt.Printf("%sError: failed to open ACPI call interface. Information about the error:%s\n %v\n", FgRed, Reset, err)
	}
	defer file.Close()

	// Read the response
	scanner := bufio.NewScanner(file)
	if !scanner.Scan() {
		fmt.Printf("%sError: No data returned from ACPI.%s\n\n", FgRed, Reset)
		return
	}

	// Sanitize string. Convert it from C-style strings (char[] including '\0' at the end) to normal string and print it out.
	fmt.Println(strings.Trim(scanner.Text(), "\x00\n "))
	os.Stdout.Sync() // Flush the buffer
}

// Getters
func getStatus(command string) {
	writeAcpiCall(command, false)

	readAcpiCall()
}

// Setters
func setPerformanceProfile(mode int) {
	if mode == 0 {
		writeAcpiCall(SetPerformanceModeIntelligentCooling, true)
	}

	if mode == 1 {
		writeAcpiCall(SetPerformanceModeExtremePerformance, true)
	}

	if mode == 2 {
		writeAcpiCall(SetPerformanceModePowerSaving, true)
	}
}

func setConservation(mode int) {
	if mode == 0 {
		writeAcpiCall(SetBattConservationOff, true)
	}

	if mode == 1 {
		writeAcpiCall(SetBattConservationOn, true)
	}

}

func setRapidCharge(mode int) {
	if mode == 0 {
		writeAcpiCall(SetRapidChargeOff, true)
	}

	if mode == 1 {
		writeAcpiCall(SetRapidChargeOn, true)
	}
}
