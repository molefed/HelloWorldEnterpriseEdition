import * as React from 'react';
import {useState} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import {makeStyles, Theme} from "@material-ui/core/styles";
import {Copyright} from "./Copyright";
import * as userService from "../../service/UserService";

const useStyles = makeStyles((theme: Theme) => ({
    topBox: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        background: theme.palette.secondary.main,
    },
    formBox: {
        width: '100%', // Fix IE11 issue.
        marginTop: theme.spacing(3),
    },
    buttonSubmit: {
        marginTop: theme.spacing(3),
        marginBottom: theme.spacing(2),
        width: '100%'
    }

}));

export default function Recovery() {
    const classes = useStyles();

    const [email, setEmail] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        await userService.recovery(email);
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Box className={classes.topBox}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    Recovery
                </Typography>
                <Box
                    component="form"
                    className={classes.formBox}
                    onSubmit={handleSubmit}
                >
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                id="email"
                                label="Email Address"
                                name="email"
                                autoComplete="email"
                                onChange={e => setEmail(e.target.value)}
                            />
                        </Grid>
                    </Grid>
                    <Button type="submit" variant="contained" className={classes.buttonSubmit}>
                        Recovery
                    </Button>
                    <Grid container /*justifyContent="flex-end"*/>
                        <Grid item>
                            <Link href="/signin" variant="body2">
                                Already have an account? Sign in
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
            <Copyright sx={{mt: 5}}/>
        </Container>
    );
}
