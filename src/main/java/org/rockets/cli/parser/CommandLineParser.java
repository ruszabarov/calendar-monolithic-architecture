package org.rockets.cli.parser;

import org.rockets.cli.commands.CreateCommand;
import org.rockets.cli.commands.DeleteCommand;
import org.rockets.cli.commands.ListCommand;
import org.rockets.cli.commands.UpdateCommand;
import org.rockets.cli.common.HelpOption;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import static picocli.CommandLine.Option;

@Command(
        subcommands = {CreateCommand.class, ListCommand.class, UpdateCommand.class, DeleteCommand.class},
        versionProvider = VersionProvider.class
)
public class CommandLineParser {

    @Mixin
    private HelpOption helpOption;
    @Option(names = {"-v", "--version"}, versionHelp = true, description = "Print version information and exit.")
    private boolean versionHelpRequested;
}
